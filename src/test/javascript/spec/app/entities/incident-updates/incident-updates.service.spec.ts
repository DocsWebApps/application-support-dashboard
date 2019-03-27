/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { of } from 'rxjs';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IncidentUpdatesService } from 'app/entities/incident-updates/incident-updates.service';
import { IIncidentUpdates, IncidentUpdates } from 'app/shared/model/incident-updates.model';

describe('Service Tests', () => {
    describe('IncidentUpdates Service', () => {
        let injector: TestBed;
        let service: IncidentUpdatesService;
        let httpMock: HttpTestingController;
        let elemDefault: IIncidentUpdates;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IncidentUpdatesService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new IncidentUpdates(0, currentDate, 'AAAAAAA');
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                service
                    .find(123)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: elemDefault }));

                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should create a IncidentUpdates', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        updatedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new IncidentUpdates(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a IncidentUpdates', async () => {
                const returnedFromService = Object.assign(
                    {
                        updatedAt: currentDate.format(DATE_FORMAT),
                        updateText: 'BBBBBB'
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .update(expected)
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'PUT' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should return a list of IncidentUpdates', async () => {
                const returnedFromService = Object.assign(
                    {
                        updatedAt: currentDate.format(DATE_FORMAT),
                        updateText: 'BBBBBB'
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        updatedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .query(1, expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a IncidentUpdates', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
