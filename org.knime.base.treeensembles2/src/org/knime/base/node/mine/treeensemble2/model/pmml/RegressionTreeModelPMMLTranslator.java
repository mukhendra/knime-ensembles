/*
 * ------------------------------------------------------------------------
 *
 *  Copyright by KNIME GmbH, Konstanz, Germany
 *  Website: http://www.knime.org; Email: contact@knime.org
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License, Version 3, as
 *  published by the Free Software Foundation.
 *
 *  This program is distributed in the hope that it will be useful, but
 *  WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, see <http://www.gnu.org/licenses>.
 *
 *  Additional permission under GNU GPL version 3 section 7:
 *
 *  KNIME interoperates with ECLIPSE solely via ECLIPSE's plug-in APIs.
 *  Hence, KNIME and ECLIPSE are both independent programs and are not
 *  derived from each other. Should, however, the interpretation of the
 *  GNU GPL Version 3 ("License") under any applicable laws result in
 *  KNIME and ECLIPSE being a combined program, KNIME GMBH herewith grants
 *  you the additional permission to use and propagate KNIME together with
 *  ECLIPSE with only the license terms in place for ECLIPSE applying to
 *  ECLIPSE and the GNU GPL Version 3 applying for KNIME, provided the
 *  license terms of ECLIPSE themselves allow for the respective use and
 *  propagation of ECLIPSE together with KNIME.
 *
 *  Additional permission relating to nodes for KNIME that extend the Node
 *  Extension (and in particular that are based on subclasses of NodeModel,
 *  NodeDialog, and NodeView) and that only interoperate with KNIME through
 *  standard APIs ("Nodes"):
 *  Nodes are deemed to be separate and independent programs and to not be
 *  covered works.  Notwithstanding anything to the contrary in the
 *  License, the License does not apply to Nodes, you are not required to
 *  license Nodes under the License, and you are granted a license to
 *  prepare and propagate Nodes, in each case even if such Nodes are
 *  propagated with or for interoperation with KNIME.  The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ---------------------------------------------------------------------
 *
 * History
 *   11.09.2017 (Adrian Nembach): created
 */
package org.knime.base.node.mine.treeensemble2.model.pmml;

import org.knime.base.node.mine.treeensemble2.learner.TreeNodeSignatureFactory;
import org.knime.base.node.mine.treeensemble2.model.AbstractTreeModel;
import org.knime.base.node.mine.treeensemble2.model.TreeModelRegression;
import org.knime.base.node.mine.treeensemble2.model.TreeNodeRegression;

/**
 *
 * @author Adrian Nembach, KNIME
 */
public class RegressionTreeModelPMMLTranslator extends AbstractTreeModelPMMLTranslator<TreeNodeRegression> {

    /**
     * Constructor to be called when the model should be initialized from PMML.
     */
    public RegressionTreeModelPMMLTranslator() {
        // nothing to do
    }

    /**
     * Constructor to be called when a model should be exported to PMML.
     * @param treeModel the tree model that should be written to PMML
     */
    public RegressionTreeModelPMMLTranslator(final AbstractTreeModel<TreeNodeRegression> treeModel) {
        super(treeModel);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public TreeModelRegression getTree() {
        return (TreeModelRegression)super.getTree();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected AbstractTreeModelExporter<TreeNodeRegression> getExporter() {
        return new RegressionTreeModelExporter(getTree());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected TreeModelImporter<TreeNodeRegression> getImporter(final MetaDataMapper metaDataMapper) {
        return new TreeModelImporter<TreeNodeRegression>(metaDataMapper, new LiteralConditionParser(metaDataMapper),
                new TreeNodeSignatureFactory(), RegressionContentParser.INSTANCE, RegressionTreeFactory.INSTANCE);
    }


}
