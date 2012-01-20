/*******************************************************************************
 * Copyright (c) 2012, Andrzej Zawadzki (azawadzki@gmail.com)
 * 
 * jefsr is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * jefsr is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with jefsr; if not, see <http ://www.gnu.org/licenses/>.
 ******************************************************************************/
package az.jefsr.file;

import az.jefsr.config.Config;
import az.jefsr.crypto.Coder;
import az.jefsr.util.FactoryBase;

public class NameDecoderFactory extends FactoryBase<NameDecoder> {
	
	static NameDecoderFactory instance = new NameDecoderFactory();
	static {
		NameDecoderFactory.getInstance().registerType("nameio/block", BlockNameDecoder.class);
		NameDecoderFactory.getInstance().registerType("nameio/stream", StreamNameDecoder.class);
		NameDecoderFactory.getInstance().registerType("nameio/null", NullNameDecoder.class);
	}	
	public static NameDecoderFactory getInstance() {
		return instance;
	}
	
	public NameDecoder createInstance(String nameAlg, Coder coder, Config config) {
		NameDecoder ret = null;
		Class<? extends NameDecoder> cls = fetchType(nameAlg);
		try {
			Class<?>[] ctrArgs = { Coder.class, Config.class };
			ret = cls.getConstructor(ctrArgs).newInstance(coder, config);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return ret;	
	}
	
}
