package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import ch.qos.logback.core.pattern.color.BoldCyanCompositeConverter;

/**
 * ログイン認証用設定
 * 
 * @author takahiro.araki
 *
 */
@Configuration // 設定用のクラス
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	/**
	 * ユーザー情報を検索する.
	 */
	@Autowired
	private UserDetailsService memberDetailsService;

	/**
	 * 特定のリクエストに対して「セキュリティ設定」を無視数設定など全体に関わる設定ができる。 プロジェクトのディレクトリ内に限る
	 * 
	 * @param web
	 * @throws Exception
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/css/**", "/img/**");
	}

	/**
	 *認可の設定、ログイン、ログアウトに関する設定.
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.authorizeRequests()//ページの認可に関する設定
		.antMatchers("/", "/itemList/showItemList", "/serchByName","/register",
				"/register/insertUser", "/detail","/Execute","/Execute/findOrder").permitAll()//全員が見られるページ
		.anyRequest().authenticated();//他は認証が必要
		
		http.formLogin() //ログインに関する設定
		.loginPage("/")//ログインしていない状態で、権限が必要な画面に行くと、ここに戻る
		.loginProcessingUrl("/login")//ログイン処理(HTML内のth:action　の記述)
		.failureUrl("/?error=true")//ログイン失敗時
		.defaultSuccessUrl("/itemList/showItemList", true) //ログイン成功時のページ
		.usernameParameter("email") // 認証時に使用するユーザ名のリクエストパラメータ名(今回はメールアドレスを使用)
		.passwordParameter("password"); // 認証時に使用するパスワードのリクエストパラメータ名
		
		//クッキー関連は未実装
		
		http.logout()//ログアウト関連処理
		.logoutRequestMatcher(new AntPathRequestMatcher("/logout**"))//ログアウトさせる際に遷移させるパス
		.logoutSuccessUrl("/login")//ログアウト後に遷移させるパス
		.invalidateHttpSession(false); //ログアウト後、tureだとセッションスコープを削除
		//クッキーのやつ
		
	}
	
	/**
	 *「認証」に関する設定.認証ユーザーの取得や、パスワード照合に使う設定を行う。
	 *
	 */
	@Override
	public void configure(AuthenticationManagerBuilder auth)throws Exception{
		auth.userDetailsService(memberDetailsService)
		.passwordEncoder(passwordEncoder());
	}
	
	 @Bean
	    public PasswordEncoder passwordEncoder() {
	    		return new BCryptPasswordEncoder();
	    }
	
	
}