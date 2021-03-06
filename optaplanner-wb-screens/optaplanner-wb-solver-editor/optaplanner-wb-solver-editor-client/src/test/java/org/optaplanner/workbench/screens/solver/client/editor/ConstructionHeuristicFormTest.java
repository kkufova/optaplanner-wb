/*
 * Copyright 2016 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.optaplanner.workbench.screens.solver.client.editor;

import java.util.List;

import com.google.gwtmockito.GwtMockitoTestRunner;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.optaplanner.core.config.constructionheuristic.ConstructionHeuristicType;
import org.optaplanner.core.config.heuristic.selector.entity.EntitySorterManner;
import org.optaplanner.workbench.screens.solver.model.ConstructionHeuristicPhaseConfigModel;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(GwtMockitoTestRunner.class)
public class ConstructionHeuristicFormTest {

    @Mock
    private ConstructionHeuristicFormView view;

    @Mock
    private ConstructionHeuristicPhaseConfigModel model;

    @Mock
    private PhaseConfigForm phaseConfigForm;

    private ConstructionHeuristicForm constructionHeuristicForm;

    @Before
    public void setUp() {
        constructionHeuristicForm = new ConstructionHeuristicForm(view);
        constructionHeuristicForm.setPhaseConfigForm(phaseConfigForm);
    }

    @Test
    public void initConstructionHeuristicForm() {
        verify(view).setPresenter(constructionHeuristicForm);

        ArgumentCaptor<List> constructionHeuristicTypeSelectOptionsCaptor = ArgumentCaptor.forClass(List.class);
        verify(view).initConstructionHeuristicTypeSelectOptions(constructionHeuristicTypeSelectOptionsCaptor.capture());

        List value = constructionHeuristicTypeSelectOptionsCaptor.getValue();
        assertEquals(ConstructionHeuristicType.values().length,
                     value.size());

        verify(view).initEntitySorterMannerSelectOptions(constructionHeuristicTypeSelectOptionsCaptor.capture());

        value = constructionHeuristicTypeSelectOptionsCaptor.getValue();
        assertEquals(EntitySorterManner.values().length,
                     value.size());
    }

    @Test
    public void onConstructionHeuristicTypeSelected() {
        when(model.getConstructionHeuristicType()).thenReturn(ConstructionHeuristicType.FIRST_FIT_DECREASING);
        when(model.getEntitySorterManner()).thenReturn(EntitySorterManner.DECREASING_DIFFICULTY);

        constructionHeuristicForm.setModel(model);

        constructionHeuristicForm.onConstructionHeuristicTypeSelected("FIRST_FIT");

        verify(model).setConstructionHeuristicType(ConstructionHeuristicType.FIRST_FIT);
    }

    @Test
    public void onEntitySorterMannerSelected() {
        when(model.getConstructionHeuristicType()).thenReturn(ConstructionHeuristicType.FIRST_FIT_DECREASING);
        when(model.getEntitySorterManner()).thenReturn(EntitySorterManner.DECREASING_DIFFICULTY_IF_AVAILABLE);

        constructionHeuristicForm.setModel(model);

        constructionHeuristicForm.onEntitySorterMannerSelected("DECREASING_DIFFICULTY");

        verify(model).setEntitySorterManner(EntitySorterManner.DECREASING_DIFFICULTY);
    }

    @Test
    public void onConstructionHeuristicRemoved() {
        constructionHeuristicForm.onConstructionHeuristicRemoved();

        verify(phaseConfigForm).removeConstructionHeuristic(constructionHeuristicForm);
    }

    @Test
    public void setModelNullAttribute() {
        when(model.getConstructionHeuristicType()).thenReturn(null).thenReturn(ConstructionHeuristicType.FIRST_FIT_DECREASING);
        when(model.getEntitySorterManner()).thenReturn(null).thenReturn(EntitySorterManner.DECREASING_DIFFICULTY_IF_AVAILABLE);

        constructionHeuristicForm.setModel(model);

        verify(model).setConstructionHeuristicType(ConstructionHeuristicType.FIRST_FIT);
        verify(model).setEntitySorterManner(EntitySorterManner.NONE);
    }

    @Test
    public void setModelNonNullAttribute() {
        when(model.getConstructionHeuristicType()).thenReturn(ConstructionHeuristicType.FIRST_FIT_DECREASING);
        when(model.getEntitySorterManner()).thenReturn(EntitySorterManner.DECREASING_DIFFICULTY_IF_AVAILABLE);

        constructionHeuristicForm.setModel(model);

        verify(model,
               times(0)).setConstructionHeuristicType(ConstructionHeuristicType.FIRST_FIT);
        verify(model,
               times(0)).setEntitySorterManner(EntitySorterManner.NONE);
    }
}
