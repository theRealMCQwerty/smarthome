/**
 * Copyright (c) 2014,2017 Contributors to the Eclipse Foundation
 *
 * See the NOTICE file(s) distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License 2.0 which is available at
 * http://www.eclipse.org/legal/epl-2.0
 *
 * SPDX-License-Identifier: EPL-2.0
 */
package org.eclipse.smarthome.core.thing

import static org.hamcrest.CoreMatchers.*
import static org.junit.Assert.*
import static org.junit.matchers.JUnitMatchers.*

import org.eclipse.smarthome.config.core.Configuration
import org.eclipse.smarthome.core.thing.binding.ThingFactory
import org.eclipse.smarthome.core.thing.type.ThingTypeBuilder
import org.junit.Before
import org.junit.Test

/**
 * Testing thing properties.
 *
 * @author Thomas Höfer - Initial contribution
 */
class ThingPropertiesTest {

    def properties = ["key1":"value1", "key2":"value2"]
    def thing

    @Before
    void setup() {
        def thingType = ThingTypeBuilder.instance(new ThingTypeUID("bindingId", "thingTypeId"), "label").withProperties(properties).build();
        thing = ThingFactory.createThing(thingType, new ThingUID(thingType.getUID(), "thingId"), new Configuration())
    }

    @Test
    void 'test get property'() {
        assertThat thing.getProperties().size(), is(2)
        assertThat thing.getProperties().get("key1"), is("value1")
        assertThat thing.getProperties().get("key2"), is("value2")
    }

    @Test
    void 'test set new property'() {
        thing.setProperty("key3", "value3");

        assertThat thing.getProperties().size(), is(3)
        assertThat thing.getProperties().get("key1"), is("value1")
        assertThat thing.getProperties().get("key2"), is("value2")
        assertThat thing.getProperties().get("key3"), is("value3")
    }

    @Test
    void 'test set new property value'() {
        def value = thing.setProperty("key2", "value3");

        assertThat value, is("value2")
        assertThat thing.getProperties().size(), is(2)
        assertThat thing.getProperties().get("key1"), is("value1")
        assertThat thing.getProperties().get("key2"), is("value3")
    }

    @Test
    void 'test remove property'() {
        def value = thing.setProperty("key1", null);

        assertThat value, is("value1")
        assertThat thing.getProperties().size(), is(1)
        assertThat thing.getProperties().get("key2"), is("value2")
    }

    @Test(expected=IllegalArgumentException)
    void 'test set property with null name'() {
        thing.setProperty(null, "");
    }

    @Test(expected=IllegalArgumentException)
    void 'test set property with empty name'() {
        thing.setProperty("", "");
    }
}
