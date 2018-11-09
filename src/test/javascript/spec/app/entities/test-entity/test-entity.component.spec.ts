/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SebHipsterTestModule } from '../../../test.module';
import { TestEntityComponent } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.component';
import { TestEntityService } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.service';
import { TestEntity } from '../../../../../../main/webapp/app/entities/test-entity/test-entity.model';

describe('Component Tests', () => {

    describe('TestEntity Management Component', () => {
        let comp: TestEntityComponent;
        let fixture: ComponentFixture<TestEntityComponent>;
        let service: TestEntityService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SebHipsterTestModule],
                declarations: [TestEntityComponent],
                providers: [
                    TestEntityService
                ]
            })
            .overrideTemplate(TestEntityComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TestEntityComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TestEntityService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new TestEntity(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.testEntities[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
