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

import java.io.IOException;

import javax.inject.Singleton;

import org.bukkit.plugin.Plugin;
import org.morganm.mBukkitLib.Debug;
import org.morganm.mBukkitLib.Logger;
import org.morganm.mBukkitLib.LoggerImpl;
import org.morganm.mBukkitLib.PermissionSystem;
import org.morganm.mBukkitLib.Teleport;
import org.morganm.mBukkitLib.i18n.Colors;
import org.morganm.mBukkitLib.i18n.Locale;
import org.morganm.mBukkitLib.i18n.LocaleConfig;
import org.morganm.mBukkitLib.i18n.LocaleFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.Scopes;
import com.google.inject.assistedinject.FactoryModuleBuilder;

/** This module tells Guice how to wire together all dependencies
 * for the plugin.
 * 
 * @author morganm
 *
 */
public class LiftSignModule extends AbstractModule {
	private final Plugin plugin;
	private final LocaleConfig localeConfig;
	private Locale locale;
	private PermissionSystem permSystem;

	public LiftSignModule(Plugin plugin, LocaleConfig localeConfig) {
		this.plugin = plugin;
		this.localeConfig = localeConfig;
	}
	
	@Override
	protected void configure() {
		bind(Logger.class)
			.to(LoggerImpl.class)
			.in(Scopes.SINGLETON);
//		bind(java.util.logging.Logger.class)
//			.toInstance(plugin.getLogger());
		bind(SignCache.class)
			.in(Scopes.SINGLETON);
		bind(Teleport.class)
			.in(Scopes.SINGLETON);
		bind(Debug.class)
			.in(Scopes.SINGLETON);
		bind(Colors.class)
			.in(Scopes.SINGLETON);
		
		
		install(new FactoryModuleBuilder()
			.implement(SignDetail.class, SignDetail.class)
			.build(SignFactory.class)
		);
	}
	
	@Provides
	@Singleton
	protected PermissionSystem providePermissionSystem() {
		if( permSystem == null )
			permSystem = new org.morganm.mBukkitLib.PermissionSystem(plugin, plugin.getLogger());
		return permSystem;
	}
	
	@Provides
	protected Plugin providePlugin() {
		return plugin;
	}
	
	@Provides
	protected Locale provideLocale() throws IOException {
        if( locale == null ) {
			locale = LocaleFactory.getLocale();
			locale.load(localeConfig);
        }
		
		return locale;
	}
}
