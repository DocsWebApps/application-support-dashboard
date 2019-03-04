import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IApp } from 'app/shared/model/app.model';

type EntityResponseType = HttpResponse<IApp>;
type EntityArrayResponseType = HttpResponse<IApp[]>;

@Injectable({ providedIn: 'root' })
export class AppService {
    public resourceUrl = SERVER_API_URL + 'api/apps';

    constructor(protected http: HttpClient) {}

    create(app: IApp): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(app);
        return this.http
            .post<IApp>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(app: IApp): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(app);
        return this.http
            .put<IApp>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IApp>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IApp[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    protected convertDateFromClient(app: IApp): IApp {
        const copy: IApp = Object.assign({}, app, {
            lastProblemDate: app.lastProblemDate != null && app.lastProblemDate.isValid() ? app.lastProblemDate.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.lastProblemDate = res.body.lastProblemDate != null ? moment(res.body.lastProblemDate) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((app: IApp) => {
                app.lastProblemDate = app.lastProblemDate != null ? moment(app.lastProblemDate) : null;
            });
        }
        return res;
    }
}
