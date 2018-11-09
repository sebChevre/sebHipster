import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TestEntity } from './test-entity.model';
import { TestEntityPopupService } from './test-entity-popup.service';
import { TestEntityService } from './test-entity.service';

@Component({
    selector: 'jhi-test-entity-delete-dialog',
    templateUrl: './test-entity-delete-dialog.component.html'
})
export class TestEntityDeleteDialogComponent {

    testEntity: TestEntity;

    constructor(
        private testEntityService: TestEntityService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.testEntityService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'testEntityListModification',
                content: 'Deleted an testEntity'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-test-entity-delete-popup',
    template: ''
})
export class TestEntityDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private testEntityPopupService: TestEntityPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.testEntityPopupService
                .open(TestEntityDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
