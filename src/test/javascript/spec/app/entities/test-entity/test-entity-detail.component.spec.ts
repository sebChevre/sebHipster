/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SebHipsterTestModule } from '../../../test.module';
import { TestEntityDetailComponent } from '../../../../../../main/webapp/app/entities/test-entity/test-entity-detail.component';
import { TestEntityService } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.service';
import { TestEntity } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.model';

describe('Component Tests', () => {

    describe('TestEntity Management Detail Component', () => {
        let comp: TestEntityDetailComponent;
        let fixture: ComponentFixture<TestEntityDetailComponent>;
        let service: TestEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SebHipsterTestModule],
                declarations: [TestEntityDetailComponent],
                providers: [
                    TestEntityService
                ]
            })
            .overrideTemplate(TestEntityDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestEntityDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new TestEntity(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.testEntity).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
