import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { TestEntity } from './test-entity.model';
import { createRequestOption } from '../../shared';

export type EntityResponseType = HttpResponse<TestEntity>;

@Injectable()
export class TestEntityService {

    private resourceUrl =  SERVER_API_URL + 'api/test-entities';

    constructor(private http: HttpClient) { }

    create(testEntity: TestEntity): Observable<EntityResponseType> {
        const copy = this.convert(testEntity);
        return this.http.post<TestEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    update(testEntity: TestEntity): Observable<EntityResponseType> {
        const copy = this.convert(testEntity);
        return this.http.put<TestEntity>(this.resourceUrl, copy, { observe: 'response' })
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<TestEntity>(`${this.resourceUrl}/${id}`, { observe: 'response'})
            .map((res: EntityResponseType) => this.convertResponse(res));
    }

    query(req?: any): Observable<HttpResponse<TestEntity[]>> {
        const options = createRequestOption(req);
        return this.http.get<TestEntity[]>(this.resourceUrl, { params: options, observe: 'response' })
            .map((res: HttpResponse<TestEntity[]>) => this.convertArrayResponse(res));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response'});
    }

    private convertResponse(res: EntityResponseType): EntityResponseType {
        const body: TestEntity = this.convertItemFromServer(res.body);
        return res.clone({body});
    }

    private convertArrayResponse(res: HttpResponse<TestEntity[]>): HttpResponse<TestEntity[]> {
        const jsonResponse: TestEntity[] = res.body;
        const body: TestEntity[] = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            body.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return res.clone({body});
    }

    /**
     * Convert a returned JSON object to TestEntity.
     */
    private convertItemFromServer(testEntity: TestEntity): TestEntity {
        const copy: TestEntity = Object.assign({}, testEntity);
        return copy;
    }

    /**
     * Convert a TestEntity to a JSON which can be sent to the server.
     */
    private convert(testEntity: TestEntity): TestEntity {
        const copy: TestEntity = Object.assign({}, testEntity);
        return copy;
    }
}
