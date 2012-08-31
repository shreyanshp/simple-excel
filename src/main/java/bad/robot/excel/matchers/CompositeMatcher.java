/*
 * Copyright (c) 2012, bad robot (london) ltd.
 *
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package bad.robot.excel.matchers;

import org.hamcrest.Description;
import org.hamcrest.Factory;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import java.util.Arrays;

class CompositeMatcher<T> extends TypeSafeDiagnosingMatcher<T> {

    private final Iterable<Matcher<T>> matchers;

    @Factory
    static <T> TypeSafeDiagnosingMatcher<T> allOf(Iterable<Matcher<T>> matchers) {
        return new CompositeMatcher<T>(matchers);
    }

    @Factory
    static <T> TypeSafeDiagnosingMatcher<T> allOf(Matcher<T>... matchers) {
        return allOf(Arrays.asList(matchers));
    }

    private CompositeMatcher(Iterable<Matcher<T>> matchers) {
        this.matchers = matchers;
    }

    @Override
    protected boolean matchesSafely(T actual, Description mismatch) {
        Mismatches<T> mismatches = new Mismatches<T>();
        mismatches.discover(actual, matchers);
        if (mismatches.found())
            mismatches.describeTo(mismatch, actual);
        return !mismatches.found();
    }

    @Override
    public void describeTo(Description description) {
        description.appendList("", " ", "", matchers);
    }

}
