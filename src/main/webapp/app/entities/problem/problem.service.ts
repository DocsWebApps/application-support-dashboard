import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProblem } from 'app/shared/model/problem.model';
type EntityResponseType = HttpResponse<IProblem>;
type EntityArrayResponseType = HttpResponse<IProblem[]>;

@Injectable({ providedIn: 'root' })
export class ProblemService {
    private _selectedStatus = 'ALL';
    private _selectedPriority = 'ALL';
    public resourceUrl = SERVER_API_URL + 'api/problems';

    constructor(protected http: HttpClient) {}

    get selectedStatus(): string {
        return this._selectedStatus;
    }

    set selectedStatus(value: string) {
        this._selectedStatus = value;
    }

    get selectedPriority(): string {
        return this._selectedPriority;
    }

    set selectedPriority(value: string) {
        this._selectedPriority = value;
    }

    create(problem: IProblem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(problem);
        return this.http
            .post<IProblem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(problem: IProblem): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(problem);
        return this.http
            .put<IProblem>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProblem>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(status: string, priority: string, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProblem[]>(`${this.resourceUrl}/${status}/${priority}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(problem: IProblem): IProblem {
        const copy: IProblem = Object.assign({}, problem, {
            openedAt: problem.openedAt != null && problem.openedAt.isValid() ? problem.openedAt.format(DATE_FORMAT) : null,
            closedAt: problem.closedAt != null && problem.closedAt.isValid() ? problem.closedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.openedAt = res.body.openedAt != null ? moment(res.body.openedAt) : null;
            res.body.closedAt = res.body.closedAt != null ? moment(res.body.closedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((problem: IProblem) => {
                problem.openedAt = problem.openedAt != null ? moment(problem.openedAt) : null;
                problem.closedAt = problem.closedAt != null ? moment(problem.closedAt) : null;
            });
        }
        return res;
    }
}
