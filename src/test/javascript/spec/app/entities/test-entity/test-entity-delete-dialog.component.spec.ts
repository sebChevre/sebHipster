/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SebHipsterTestModule } from '../../../test.module';
import { TestEntityDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/test-entity/test-entity-delete-dialog.component';
import { TestEntityService } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.service';

describe('Component Tests', () => {

    describe('TestEntity Management Delete Component', () => {
        let comp: TestEntityDeleteDialogComponent;
        let fixture: ComponentFixture<TestEntityDeleteDialogComponent>;
        let service: TestEntityService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SebHipsterTestModule],
                declarations: [TestEntityDeleteDialogComponent],
                providers: [
                    TestEntityService
                ]
            })
            .overrideTemplate(TestEntityDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestEntityDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestEntityService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
