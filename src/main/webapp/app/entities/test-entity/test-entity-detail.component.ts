import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { TestEntity } from './test-entity.model';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity-detail',
    templateUrl: './test-entity-detail.component.html'
})
export class TestEntityDetailComponent implements OnInit, OnDestroy {

    testEntity: TestEntity;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private testEntityService: TestEntityService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTestEntities();
    }

    load(id) {
        this.testEntityService.find(id)
            .subscribe((testEntityResponse: HttpResponse<TestEntity>) => {
                this.testEntity = testEntityResponse.body;
            });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTestEntities() {
        this.eventSubscriber = this.eventManager.subscribe(
            'testEntityListModification',
            (response) => this.load(this.testEntity.id)
        );
    }
}
