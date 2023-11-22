package vitorsb.project.logidataprocess.config

import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.context.annotation.Configuration
import org.springframework.test.context.ActiveProfiles


@Configuration
@SpringBootConfiguration
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class JpaConfig
