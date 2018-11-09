import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TestEntity } from './test-entity.model';
import { TestEntityService } from './test-entity.service';
import { Principal } from '../../shared';

@Component({
    selector: 'jhi-test-entity',
    templateUrl: './test-entity.component.html'
})
export class TestEntityComponent implements OnInit, OnDestroy {
testEntities: TestEntity[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private testEntityService: TestEntityService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.testEntityService.query().subscribe(
            (res: HttpResponse<TestEntity[]>) => {
                this.testEntities = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInTestEntities();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: TestEntity) {
        return item.id;
    }
    registerChangeInTestEntities() {
        this.eventSubscriber = this.eventManager.subscribe('testEntityListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
