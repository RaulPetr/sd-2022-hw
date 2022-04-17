import { Component, OnInit } from '@angular/core';
import {AuthenticationService} from "../../../api/services/authentication.service";
import {ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree} from "@angular/router";
import {LoginRequest} from "../../models/login-request.model";
import {Observable} from 'rxjs';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  form: LoginRequest = new LoginRequest();
  isLoggedIn = false;
  isLoginFailed = false;
  errorMessage = '';
  roles: string[] = [];

  constructor(private authenticationService: AuthenticationService,
              private router: Router) {
  }

  ngOnInit(): void {
  }

  attemptLogin(): void {

    this.authenticationService.login(this.form).subscribe(
      data => {
        localStorage.setItem('user', JSON.stringify(data));
        this.isLoginFailed = false;
        this.isLoggedIn = true;
        // this.authenticationService.getAllUsers().subscribe(
        //   users => console.log(users)
        // )
        this.router.navigate(['/employee'])
      },
      err => {
        console.log('error')
        this.errorMessage = err.error.message;
        this.isLoginFailed = true;
      }
    );
  }

  attemptRegister(): void {
    this.router.navigate(['/register']);
  }

  reloadPage(): void {
    window.location.reload();
  }
}
