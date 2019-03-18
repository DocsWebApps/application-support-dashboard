import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProblemUpdates } from 'app/shared/model/problem-updates.model';

type EntityResponseType = HttpResponse<IProblemUpdates>;
type EntityArrayResponseType = HttpResponse<IProblemUpdates[]>;

@Injectable({ providedIn: 'root' })
export class ProblemUpdatesService {
    public resourceUrl = SERVER_API_URL + 'api/problem-updates';
    private _problemID;
    private _returnRoute;

    constructor(protected http: HttpClient) {}

    get returnRoute() {
        return this._returnRoute;
    }

    set returnRoute(value) {
        this._returnRoute = value;
    }

    get problemID() {
        return this._problemID;
    }

    set problemID(value) {
        this._problemID = value;
    }

    create(problemUpdates: IProblemUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(problemUpdates);
        return this.http
            .post<IProblemUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(problemUpdates: IProblemUpdates): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(problemUpdates);
        return this.http
            .put<IProblemUpdates>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProblemUpdates>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(id: number, req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProblemUpdates[]>(`${this.resourceUrl}/problem/${id}`, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(problemUpdates: IProblemUpdates): IProblemUpdates {
        const copy: IProblemUpdates = Object.assign({}, problemUpdates, {
            updatedAt:
                problemUpdates.updatedAt != null && problemUpdates.updatedAt.isValid() ? problemUpdates.updatedAt.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.updatedAt = res.body.updatedAt != null ? moment(res.body.updatedAt) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((problemUpdates: IProblemUpdates) => {
                problemUpdates.updatedAt = problemUpdates.updatedAt != null ? moment(problemUpdates.updatedAt) : null;
            });
        }
        return res;
    }
}
