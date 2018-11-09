import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { SebHipsterSharedModule } from '../../shared';
import {
    TestEntityService,
    TestEntityPopupService,
    TestEntityComponent,
    TestEntityDetailComponent,
    TestEntityDialogComponent,
    TestEntityPopupComponent,
    TestEntityDeletePopupComponent,
    TestEntityDeleteDialogComponent,
    testEntityRoute,
    testEntityPopupRoute,
} from './';

const ENTITY_STATES = [
    ...testEntityRoute,
    ...testEntityPopupRoute,
];

@NgModule({
    imports: [
        SebHipsterSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        TestEntityComponent,
        TestEntityDetailComponent,
        TestEntityDialogComponent,
        TestEntityDeleteDialogComponent,
        TestEntityPopupComponent,
        TestEntityDeletePopupComponent,
    ],
    entryComponents: [
        TestEntityComponent,
        TestEntityDialogComponent,
        TestEntityPopupComponent,
        TestEntityDeleteDialogComponent,
        TestEntityDeletePopupComponent,
    ],
    providers: [
        TestEntityService,
        TestEntityPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class SebHipsterTestEntityModule {}
