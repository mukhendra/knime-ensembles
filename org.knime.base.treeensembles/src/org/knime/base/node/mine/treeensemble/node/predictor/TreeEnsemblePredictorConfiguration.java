/*
 * ------------------------------------------------------------------------
 *
 *  Copyright (C) 2003 - 2013
 *  University of Konstanz, Germany and
 *  KNIME GmbH, Konstanz, Germany
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
 *  propagated with or for interoperation with KNIME. The owner of a Node
 *  may freely choose the license terms applicable to such Node, including
 *  when such Node is propagated with or for interoperation with KNIME.
 * ------------------------------------------------------------------------
 *
 * History
 *   Jan 10, 2012 (wiswedel): created
 */
package org.knime.base.node.mine.treeensemble.node.predictor;

import org.knime.core.node.InvalidSettingsException;
import org.knime.core.node.NodeSettingsRO;
import org.knime.core.node.NodeSettingsWO;
import org.knime.core.node.NotConfigurableException;

/**
 *
 * @author Bernd Wiswedel, KNIME.com, Zurich, Switzerland
 */
public final class TreeEnsemblePredictorConfiguration {

    /**  */
    public static final String getPredictColumnName(final boolean isRegression) {
        return isRegression ? "Tree Ensemble Response" : "Tree Ensemble Prediction";
    }

    private final boolean m_isRegression;

    private boolean m_appendPredictionConfidence = true;

    private boolean m_appendClassConfidences = false;

    private boolean m_appendModelCount = false;

    private String m_predictionColumnName;

    /**
     *
     */
    public TreeEnsemblePredictorConfiguration(final boolean isRegression) {
        m_isRegression = isRegression;
        m_predictionColumnName = getPredictColumnName(isRegression);
    }

    /** @return the appendPredictionConfidence */
    public boolean isAppendPredictionConfidence() {
        return m_appendPredictionConfidence;
    }

    /** @param value the appendPredictionConfidence to set */
    public void setAppendPredictionConfidence(final boolean value) {
        m_appendPredictionConfidence = value;
    }

    /** @return the appendConfidenceValues */
    public boolean isAppendClassConfidences() {
        return m_appendClassConfidences;
    }

    /** @param value the appendClassConfidences to set */
    public void setAppendClassConfidences(final boolean value) {
        m_appendClassConfidences = value;
    }

    /** @return the predictionColumnName */
    public String getPredictionColumnName() {
        return m_predictionColumnName;
    }

    /** @param predictionColumnName the predictionColumnName to set */
    public void setPredictionColumnName(final String predictionColumnName) {
        m_predictionColumnName = predictionColumnName;
    }

    /** @return the appendModelCount */
    public boolean isAppendModelCount() {
        return m_appendModelCount;
    }

    /** @param appendModelCount the appendModelCount to set */
    public void setAppendModelCount(final boolean appendModelCount) {
        m_appendModelCount = appendModelCount;
    }

    public void save(final NodeSettingsWO settings) {
        settings.addBoolean("appendPredictionConfidence", m_appendPredictionConfidence);
        settings.addBoolean("appendClassConfidences", m_appendClassConfidences);
        settings.addBoolean("appendModelCount", m_appendModelCount);
        settings.addString("predictionColumnName", m_predictionColumnName);
    }

    public void loadInDialog(final NodeSettingsRO settings) throws NotConfigurableException {
        m_appendPredictionConfidence = settings.getBoolean("appendPredictionConfidence", true);
        m_appendClassConfidences = settings.getBoolean("appendClassConfidences", false);
        String defColName = getPredictColumnName(m_isRegression);
        m_predictionColumnName = settings.getString("predictionColumnName", defColName);
        m_appendModelCount = settings.getBoolean("appendModelCount", false);
        if (m_predictionColumnName == null || m_predictionColumnName.isEmpty()) {
            m_predictionColumnName = defColName;
        }
    }

    public void loadInModel(final NodeSettingsRO settings) throws InvalidSettingsException {
        m_appendPredictionConfidence = settings.getBoolean("appendPredictionConfidence");
        m_appendClassConfidences = settings.getBoolean("appendClassConfidences");
        m_predictionColumnName = settings.getString("predictionColumnName");
        m_appendModelCount = settings.getBoolean("appendModelCount");
        if (m_predictionColumnName == null || m_predictionColumnName.isEmpty()) {
            throw new InvalidSettingsException("Prediction column name must not be empty");
        }
    }

    public static TreeEnsemblePredictorConfiguration createDefault(final boolean isRegression) {
        return new TreeEnsemblePredictorConfiguration(isRegression);
    }

}
