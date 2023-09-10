import {Injectable} from "@angular/core";
import {BehaviorSubject, Observable, tap} from "rxjs";
import {User} from "../../entity/user";
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Router} from "@angular/router";

@Injectable({
  providedIn: 'root'
})
export class UserService {
  currentLoginUserSubject = new BehaviorSubject<User | null | undefined>(undefined);
  currentLoginUser$ = this.currentLoginUserSubject.asObservable();

  constructor(private httpClient: HttpClient,
              private router: Router) {
  }

  login(user: {username: string, password: string}): Observable<User> {
    // 新建Headers，并添加认证信息
    let headers = new HttpHeaders();
    // 添加 content-type
    headers = headers.append('Content-Type', 'application/x-www-form-urlencoded');
    // 添加认证信息
    headers = headers.append('Authorization',
      'Basic ' + btoa(user.username + ':' + encodeURIComponent(user.password)));
    // 发起get请求并返回
    return this.httpClient.get<User>(`/user/login`, {headers})
      .pipe(tap(data => this.setCurrentLoginUser(data)));
  }
  initCurrentLoginUser(callback?: () => void): Observable<User> {
    return new Observable<User>(subscriber => {
      this.httpClient.get<User>(`/user/me`)
        .subscribe({
          next: (user: User) => {
            this.setCurrentLoginUser(user);
            subscriber.next();
          },
          error: () => {
            if (callback) {
              callback();
            }
            subscriber.error();
          },
          complete: () => {
            if (callback) {
              callback();
            }
            subscriber.complete();
          }
        });
    });
  }

  setCurrentLoginUser(user: User | undefined): void {
    this.currentLoginUserSubject.next(user);
    if (user === undefined) {
      this.router.navigateByUrl('/login').then();
    }
  }

  getCurrentLoginUser$(): Observable<User | null | undefined> {
    return this.currentLoginUser$;
  }
}
