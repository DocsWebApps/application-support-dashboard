import { Injectable } from '@angular/core';
import { Name } from 'app/shared/model/name.model';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { createRequestOption } from 'app/shared/index';
import { Observable } from 'rxjs';
import { SERVER_API_URL } from 'app/app.constants';
import { map } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class NameService {
    private resourceUrl = SERVER_API_URL + 'api/apps/name';

    constructor(private http: HttpClient) {}

    getName(req?: any): Observable<HttpResponse<Name>> {
        const options = createRequestOption(req);
        return this.http.get<Name>(this.resourceUrl, { params: options, observe: 'response' }).pipe(
            map(res => {
                const body: Name = res.body;
                return res.clone({ body });
            })
        );
    }
}
