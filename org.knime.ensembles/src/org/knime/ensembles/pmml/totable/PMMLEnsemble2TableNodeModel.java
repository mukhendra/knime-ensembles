/*
 * ------------------------------------------------------------------------
 *  Copyright by KNIME AG, Zurich, Switzerland
 *  Website: http://www.knime.com; Email: contact@knime.com
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
 *  KNIME and ECLIPSE being a combined program, KNIME AG herewith grants
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
 * ------------------------------------------------------------------------
 */
package org.knime.ensembles.pmml.totable;

import java.io.File;
import java.io.IOException;

import org.dmg.pmml.PMMLDocument;
import org.knime.core.data.DataCell;
import org.knime.core.data.DataColumnSpecCreator;
import org.knime.core.data.DataTableSpec;
import org.knime.core.data.def.DefaultRow;
import org.knime.core.data.def.DoubleCell;
import org.knime.core.data.util.LockedSupplier;
import org.knime.core.data.xml.PMMLCell;
import org.knime.core.node.BufferedDataContainer;
import org.knime.core.node.BufferedDataTable;
import org.knime.core.node.CanceledExecutionException;
import org.knime.core.node.ExecutionContext;
import org.knime.core.node.ExecutionMonitor;
import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeModel;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.port.PortObject;
import org.knime.core.node.port.PortObjectSpec;
import org.knime.core.node.port.PortType;
import org.knime.core.node.port.pmml.PMMLPortObject;
import org.knime.ensembles.pmml.PMMLMiningModelTranslator;
import org.w3c.dom.Document;



/**
 * This is the model implementation of PMMLEnsemble2Table.
 * This node takes an ensemble from a pmml document and creates a table containing one document for each model
 * it finds in the ensemble.
 *
 * @author Alexander Fillbrunn, Universitaet Konstanz
 * @since 2.8
 */
public class PMMLEnsemble2TableNodeModel extends NodeModel {

    /**
     * Constructor for the node model.
     */
    protected PMMLEnsemble2TableNodeModel() {
        super(new PortType[]{PMMLPortObject.TYPE},
                new PortType[]{BufferedDataTable.TYPE});
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PortObject[] execute(final PortObject[] inData,
            final ExecutionContext exec) throws Exception {
        PMMLDocument pmmldoc;

        try (LockedSupplier<Document> supplier =
            ((PMMLPortObject)inData[0]).getPMMLValue().getDocumentSupplier()) {
            pmmldoc = PMMLDocument.Factory.parse(supplier.get());
        }

        PMMLMiningModelTranslator trans = new PMMLMiningModelTranslator();
        trans.initializeFrom(pmmldoc);

        BufferedDataContainer cont = exec.createDataContainer(createSpec(true));
        DataCell[] pmmlCells = trans.getPmmlCells();
        DoubleCell[] weightCells = trans.getWeightCells();

        for (int i = 0; i < pmmlCells.length; i++) {
            DefaultRow row = new DefaultRow(Integer.toString(i), pmmlCells[i], weightCells[i]);
            cont.addRowToTable(row);
        }

        cont.close();
        return new PortObject[]{cont.getTable()};
    }

    private DataTableSpec createSpec(final boolean weightAvailable) {
        DataColumnSpecCreator pmmlColSpecCreator =
            new DataColumnSpecCreator("PMML", PMMLCell.TYPE);
        // If weights are available, another column for the weights is added
        if (weightAvailable) {
            DataColumnSpecCreator weightColSpecCreator =
                new DataColumnSpecCreator("Weight", DoubleCell.TYPE);
            return new DataTableSpec(pmmlColSpecCreator.createSpec(), weightColSpecCreator.createSpec());
        } else {
            return new DataTableSpec(pmmlColSpecCreator.createSpec());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void reset() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected PortObjectSpec[] configure(final PortObjectSpec[] inSpecs)
            throws InvalidSettingsException {
        return new PortObjectSpec[]{createSpec(true)};
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveSettingsTo(final NodeSettingsWO settings) {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadValidatedSettingsFrom(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void validateSettings(final NodeSettingsRO settings)
            throws InvalidSettingsException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void loadInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    protected void saveInternals(final File internDir,
            final ExecutionMonitor exec) throws IOException,
            CanceledExecutionException {
    }

}

