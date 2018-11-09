import { BaseEntity } from './../../shared';

export class TestEntity implements BaseEntity {
    constructor(
        public id?: number,
        public test?: string,
        public desc?: string,
    ) {
    }
}
