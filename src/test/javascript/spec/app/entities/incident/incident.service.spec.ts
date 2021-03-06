/* tslint:disable max-line-length */
import { TestBed, getTestBed } from '@angular/core/testing';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';
import { take, map } from 'rxjs/operators';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { IncidentService } from 'app/entities/incident/incident.service';
import { IIncident, Incident, Severity, IssueStatus } from 'app/shared/model/incident.model';

describe('Service Tests', () => {
    describe('Incident Service', () => {
        let injector: TestBed;
        let service: IncidentService;
        let httpMock: HttpTestingController;
        let elemDefault: IIncident;
        let currentDate: moment.Moment;
        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [HttpClientTestingModule]
            });
            injector = getTestBed();
            service = injector.get(IncidentService);
            httpMock = injector.get(HttpTestingController);
            currentDate = moment();

            elemDefault = new Incident(0, currentDate, 'AAAAAAA', Severity.P1, IssueStatus.OPEN, currentDate);
        });

        describe('Service methods', async () => {
            it('should find an element', async () => {
                const returnedFromService = Object.assign(
                    {
                        openedAt: currentDate.format(DATE_FORMAT),
                        closedAt: currentDate.format(DATE_FORMAT)
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

            it('should create a Incident', async () => {
                const returnedFromService = Object.assign(
                    {
                        id: 0,
                        openedAt: currentDate.format(DATE_FORMAT),
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        openedAt: currentDate,
                        closedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .create(new Incident(null))
                    .pipe(take(1))
                    .subscribe(resp => expect(resp).toMatchObject({ body: expected }));
                const req = httpMock.expectOne({ method: 'POST' });
                req.flush(JSON.stringify(returnedFromService));
            });

            it('should update a Incident', async () => {
                const returnedFromService = Object.assign(
                    {
                        openedAt: currentDate.format(DATE_FORMAT),
                        description: 'BBBBBB',
                        severity: 'BBBBBB',
                        incidentStatus: 'BBBBBB',
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );

                const expected = Object.assign(
                    {
                        openedAt: currentDate,
                        closedAt: currentDate
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

            it('should return a list of Incident', async () => {
                const returnedFromService = Object.assign(
                    {
                        openedAt: currentDate.format(DATE_FORMAT),
                        description: 'BBBBBB',
                        severity: 'BBBBBB',
                        incidentStatus: 'BBBBBB',
                        closedAt: currentDate.format(DATE_FORMAT)
                    },
                    elemDefault
                );
                const expected = Object.assign(
                    {
                        openedAt: currentDate,
                        closedAt: currentDate
                    },
                    returnedFromService
                );
                service
                    .query('ALL', 'ALL', expected)
                    .pipe(
                        take(1),
                        map(resp => resp.body)
                    )
                    .subscribe(body => expect(body).toContainEqual(expected));
                const req = httpMock.expectOne({ method: 'GET' });
                req.flush(JSON.stringify([returnedFromService]));
                httpMock.verify();
            });

            it('should delete a Incident', async () => {
                const rxPromise = service.delete(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });

            it('should close an Incident', async () => {
                const rxPromise = service.close(123).subscribe(resp => expect(resp.ok));

                const req = httpMock.expectOne({ method: 'DELETE' });
                req.flush({ status: 200 });
            });
        });

        afterEach(() => {
            httpMock.verify();
        });
    });
});
