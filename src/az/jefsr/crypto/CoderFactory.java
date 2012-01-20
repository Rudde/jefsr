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
package az.jefsr.crypto;

import az.jefsr.config.Config;
import az.jefsr.util.FactoryBase;

public class CoderFactory extends FactoryBase<Coder> {

	static CoderFactory instance = new CoderFactory();
	static {
		CoderFactory.getInstance().registerType(AesCipher.NAME, BaseCoder.class);
		CoderFactory.getInstance().registerType(BlowfishCipher.NAME, BaseCoder.class);
		CoderFactory.getInstance().registerType(NullCipher.NAME, NullCoder.class);
	}	
	public static CoderFactory getInstance() {
		return instance;
	}
	
	public Coder createInstance(Key k, CipherAlgorithm cipher, Config config) {
		Coder ret = null;
		Class<? extends Coder> cls = fetchType(cipher.getName());
		try {
			Class<?>[] ctrArgs = { Key.class, CipherAlgorithm.class, Config.class };
			ret = cls.getConstructor(ctrArgs).newInstance(k, cipher, config);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}
		return ret;	
	}
}
