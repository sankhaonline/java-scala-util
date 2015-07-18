/**
 *  Copyright 2015 Peter Nerg
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package javascalautils.concurrent;

import javascalautils.BaseAssert;

import org.junit.Test;

/**
 * Test the class {@link PromiseImpl}
 * 
 * @author Peter Nerg
 */
public class TestPromiseImpl extends BaseAssert {
    private final Promise<String> promise = Promise.apply();

    @Test
    public void future() {
        assertNotNull(promise.future());
    }

    @Test
    public void isCompleted_notCompleted() {
        assertFalse(promise.isCompleted());
    }

    /**
     * Test a {@link Promise#success(Object)} invocation
     */
    @Test
    public void success_once() {
        promise.success("Things went well, wohooo!");
        assertTrue(promise.isCompleted());
    }

    /**
     * Test two {@link Promise#success(Object)} invocations, should render an exception on the second.
     */
    @Test(expected = IllegalStateException.class)
    public void success_twice() {
        success_once();
        promise.success("Ooops a second response, bummer");
    }

    /**
     * Test a {@link Promise#failure(throwable)} invocation
     */
    @Test
    public void failure_once() {
        promise.failure(new Exception("What a sad day!"));
        assertTrue(promise.isCompleted());
    }

    /**
     * Test two {@link Promise#failure(Throwable)} invocations, should render an exception on the second.
     */
    @Test(expected = IllegalStateException.class)
    public void failure_twice() {
        failure_once();
        promise.failure(new Exception("Scheit, another exception"));
    }

    /**
     * Test a {@link Promise#success(Object)} invocation, followed by a {@link Promise#failure(Throwable)} invocation, should render an exception on the second
     */
    @Test(expected = IllegalStateException.class)
    public void success_thenFailure() {
        success_once();
        failure_once();
    }

    /**
     * Test a {@link Promise#failure(throwable)} invocation, followed by a {@link Promise#success(Object)} invocation, should render an exception on the second
     */
    @Test(expected = IllegalStateException.class)
    public void failure_thenSuccess() {
        failure_once();
        success_once();
    }
}