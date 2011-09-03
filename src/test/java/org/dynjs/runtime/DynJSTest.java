/**
 *  Copyright 2011 dynjs contributors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.dynjs.runtime;

import org.dynjs.exception.ReferenceError;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

public class DynJSTest {

    private DynJS dynJS;
    private DynThreadContext context;
    private DynObject scope;

    @Before
    public void setUp() {
        dynJS = new DynJS();
        context = new DynThreadContext();
        scope = new DynObject();
    }

    @Test
    public void testRuntimeBootstrap() {
        dynJS.eval("print('hello world');");
    }

    @Ignore("pb - 2011-09-02") @Test(expected = ReferenceError.class)
    public void throwsReferenceErrorWhenCallAnInexistentToken() {
        dynJS.eval(scope, "print(x);", context);
    }
}
