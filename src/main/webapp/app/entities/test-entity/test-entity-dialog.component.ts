import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TestEntity } from './test-entity.model';
import { TestEntityPopupService } from './test-entity-popup.service';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity-dialog',
    templateUrl: './test-entity-dialog.component.html'
})
export class TestEntityDialogComponent implements OnInit {

    testEntity: TestEntity;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private testEntityService: TestEntityService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.testEntity.id !== undefined) {
            this.subscribeToSaveResponse(
                this.testEntityService.update(this.testEntity));
        } else {
            this.subscribeToSaveResponse(
                this.testEntityService.create(this.testEntity));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<TestEntity>>) {
        result.subscribe((res: HttpResponse<TestEntity>) =>
            this.onSaveSuccess(res.body), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess(result: TestEntity) {
        this.eventManager.broadcast({ name: 'testEntityListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-test-entity-popup',
    template: ''
})
export class TestEntityPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testEntityPopupService: TestEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.testEntityPopupService
                    .open(TestEntityDialogComponent as Component, params['id']);
            } else {
                this.testEntityPopupService
                    .open(TestEntityDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
