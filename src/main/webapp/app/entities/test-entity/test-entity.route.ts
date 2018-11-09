import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { TestEntityComponent } from './test-entity.component';
import { TestEntityDetailComponent } from './test-entity-detail.component';
import { TestEntityPopupComponent } from './test-entity-dialog.component';
import { TestEntityDeletePopupComponent } from './test-entity-delete-dialog.component';

export const testEntityRoute: Routes = [
    {
        path: 'test-entity',
        component: TestEntityComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sebHipsterApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'test-entity/:id',
        component: TestEntityDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sebHipsterApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const testEntityPopupRoute: Routes = [
    {
        path: 'test-entity-new',
        component: TestEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sebHipsterApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-entity/:id/edit',
        component: TestEntityPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sebHipsterApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'test-entity/:id/delete',
        component: TestEntityDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'sebHipsterApp.testEntity.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
