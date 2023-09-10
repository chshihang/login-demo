import {Component, OnInit} from '@angular/core';
import {FormControl, FormGroup} from "@angular/forms";
import {UserService} from "../service/user.service";
import {User} from "../../entity/user";
import {Router} from "@angular/router";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {
  loginForm = new FormGroup({
    username: new FormControl(),
    password: new FormControl()
  });


  constructor(private userService: UserService,
              private router: Router) {
  }

  ngOnInit(): void {

  }

  onSubmit(): void {
    this.login(this.loginForm.value as User);
  }

  login(user: {username: string, password: string}): void {
    this.userService.login(user)
      .subscribe((data) => {
        if (data) {
          window.sessionStorage.setItem('login', 'true');
          this.initLoginUser();
        } else {
          window.reportError('login fail')
        }
      });
  }

  private initLoginUser(description?: string) {
    this.userService.initCurrentLoginUser(() => {
      this.router.navigateByUrl('/dashboard').then();
    }).subscribe();
  }
}
