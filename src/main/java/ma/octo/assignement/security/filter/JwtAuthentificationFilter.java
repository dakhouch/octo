package ma.octo.assignement.security.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.Date;
import java.util.stream.Collectors;

public class JwtAuthentificationFilter extends UsernamePasswordAuthenticationFilter {

    AuthenticationManager authenticationManager;

    public JwtAuthentificationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    //excuted first
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
    //recuperer les donne de la requete
       String username=request.getParameter("username");
       String password=request.getParameter("password");
    //creer l'objet Authentification
       Authentication authentication=new UsernamePasswordAuthenticationToken(username,password);
        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
     //recuperer les donnes
        User user= (User) authResult.getPrincipal();
     //creation token
     //choix lalgorithme
        Algorithm algorithm=Algorithm.HMAC256("OctoInterview");
        //init token
       String token = JWT.create()
               .withExpiresAt(new Date(System.currentTimeMillis()+3600*1000))
               .withSubject(user.getUsername())
               .withClaim("roles",
                       user.getAuthorities().stream()
                               .map(GrantedAuthority::getAuthority)
                               .collect(Collectors.toList()))
               .sign(algorithm);
      // send to client
       response.addHeader("Authorization",token);
    }
}
