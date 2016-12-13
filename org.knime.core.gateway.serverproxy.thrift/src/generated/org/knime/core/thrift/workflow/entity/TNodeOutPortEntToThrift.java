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
 */
package org.knime.core.thrift.workflow.entity;

import org.knime.core.gateway.v0.workflow.entity.NodePortEnt;
import org.knime.core.gateway.v0.workflow.entity.PortTypeEnt;

import org.knime.core.gateway.v0.workflow.entity.NodeOutPortEnt;
import org.knime.core.gateway.v0.workflow.entity.builder.NodeOutPortEntBuilder;

import org.knime.core.thrift.workflow.entity.TNodeOutPortEntFromThrift.TNodeOutPortEntBuilderFromThrift;
import org.knime.core.gateway.v0.workflow.entity.builder.GatewayEntityBuilder;

import java.util.stream.Collectors;


/**
 *
 * @author Martin Horn, University of Konstanz
 */
public class TNodeOutPortEntToThrift extends TNodeOutPortEnt {

	private final NodeOutPortEnt m_e;
	
	public TNodeOutPortEntToThrift(final NodeOutPortEnt e) {
		m_e = e;
	}

	@Override
    public int getPortIndex() {
        	return m_e.getPortIndex();
        }
    
	@Override
    public TPortTypeEnt getPortType() {
            return new TPortTypeEntToThrift(m_e.getPortType());
        }
    
	@Override
    public String getPortName() {
        	return m_e.getPortName();
        }
    

    public static class TNodeOutPortEntBuilderToThrift extends TNodeOutPortEntBuilder {
    
    	private NodeOutPortEntBuilder m_b;
    	
    	public TNodeOutPortEntBuilderToThrift(final NodeOutPortEntBuilder b) {
    		m_b = b;
    	}

    
    	@Override
        public TNodeOutPortEnt build() {
            return new TNodeOutPortEntToThrift(m_b.build());
        }
        
        @Override
        public GatewayEntityBuilder<NodeOutPortEnt> wrap() {
            return new TNodeOutPortEntBuilderFromThrift(this);
        }

		@Override
        public TNodeOutPortEntBuilderToThrift setPortIndex(final int PortIndex) {
					m_b.setPortIndex(PortIndex);
		            return this;
        }
        
		@Override
        public TNodeOutPortEntBuilderToThrift setPortType(final TPortTypeEnt PortType) {
					m_b.setPortType(new TPortTypeEntFromThrift(PortType));
		            return this;
        }
        
		@Override
        public TNodeOutPortEntBuilderToThrift setPortName(final String PortName) {
					m_b.setPortName(PortName);
		            return this;
        }
        
    }

}
