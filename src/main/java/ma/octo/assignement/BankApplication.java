package ma.octo.assignement;

import ma.octo.assignement.domain.Account;
import ma.octo.assignement.domain.User;
import ma.octo.assignement.domain.Transfer;
import ma.octo.assignement.repository.AccountRepository;
import ma.octo.assignement.repository.UserRepository;
import ma.octo.assignement.repository.TransferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.util.Date;

@SpringBootApplication
public class BankApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

	@Bean
	PasswordEncoder passwordEncoder(){
		return new BCryptPasswordEncoder();
	}
	@Override
	public void run(String... strings) throws Exception {
	}
}
