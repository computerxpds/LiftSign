/*******************************************************************************
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * 
 * Copyright (c) 2012 Mark Morgan.
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 * 1. Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * Contributors:
 *     Mark Morgan - initial API and implementation
 ******************************************************************************/
/**
 * 
 */
package org.morganm.liftsign;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import org.bukkit.command.CommandSender;
import org.junit.Before;
import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

/**
 * @author morganm
 *
 */
public class TestPermissionCheck {
	private Permissions permSystemAllTrue;
	private Permissions permSystemAllFalse;
	private CommandSender mockSender;
	
	@Before
	public void setup() {
		mockSender = PowerMockito.mock(CommandSender.class);
		permSystemAllTrue = PowerMockito.mock(Permissions.class);
		when(permSystemAllTrue.has(eq(mockSender), anyString())).thenReturn(true);
		permSystemAllFalse = PowerMockito.mock(Permissions.class);
		when(permSystemAllTrue.has(eq(mockSender), anyString())).thenReturn(true);
	}
	
	@Test
	public void testCanUseNormalLift() {
		PermissionCheck pc = new PermissionCheck(permSystemAllTrue);
		assertTrue(pc.canUseNormalLift(mockSender));
		
		pc = new PermissionCheck(permSystemAllFalse);
		assertFalse(pc.canUseNormalLift(mockSender));
	}
	
	@Test
	public void testCanCreateNormalLift() {
		PermissionCheck pc = new PermissionCheck(permSystemAllTrue);
		assertTrue(pc.canCreateNormalLift(mockSender));
		
		pc = new PermissionCheck(permSystemAllFalse);
		assertFalse(pc.canCreateNormalLift(mockSender));
	}
}
