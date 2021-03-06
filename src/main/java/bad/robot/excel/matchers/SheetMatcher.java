/*
 * Copyright (c) 2012-2013, bad robot (london) ltd.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package bad.robot.excel.matchers;

import org.apache.poi.ss.usermodel.Workbook;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeDiagnosingMatcher;

import static bad.robot.excel.matchers.CompositeMatcher.allOf;
import static bad.robot.excel.matchers.SheetNameMatcher.containsSameNamedSheetsAs;
import static bad.robot.excel.matchers.SheetNumberMatcher.hasSameNumberOfSheetsAs;

public class SheetMatcher extends TypeSafeDiagnosingMatcher<Workbook> {

    private final Matcher<Workbook> matchers;

    public static SheetMatcher hasSameSheetsAs(Workbook expected) {
        return new SheetMatcher(expected);
    }

    private SheetMatcher(Workbook expected) {
        Matcher<Workbook> numberOfSheets = hasSameNumberOfSheetsAs(expected);
        Matcher<Workbook> namesOfSheets = containsSameNamedSheetsAs(expected);
        this.matchers = allOf(numberOfSheets, namesOfSheets);
    }

    @Override
    protected boolean matchesSafely(Workbook actual, Description mismatch) {
        boolean match = matchers.matches(actual);
        if (!match)
            matchers.describeMismatch(actual, mismatch);
        return match;
    }

    @Override
    public void describeTo(Description description) {
        matchers.describeTo(description);
    }


}
