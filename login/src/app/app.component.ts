import { Component } from '@angular/core';
import {Router} from "@angular/router";
import {UserService} from "./service/user.service";

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = 'login';

  constructor(private router: Router,
              private userService: UserService) {
    if (this.router && this.router.url && !this.router.url.startsWith(`/login`)) {
      this.userService.initCurrentLoginUser(() => {
      }).subscribe({
        error: () =>
          this.router.navigateByUrl('/login').then()
      });
    }
  }
}
