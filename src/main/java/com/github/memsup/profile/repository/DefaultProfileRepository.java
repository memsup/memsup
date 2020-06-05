package com.github.memsup.profile.repository;


import com.github.memsup.profile.domain.Profile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
@Slf4j
public class DefaultProfileRepository implements ProfileRepository {

    private final DataSource dataSource;

    @Override
    public Optional<Profile> getProfileWithItsUsername(final String username) {

        ProfileRepository profileRepository = null;

        try (Connection connection =
                     dataSource.getConnection()) {
            connection.setAutoCommit(false);


        } catch (SQLException e) {
            log.error(e.getMessage());
        }


        return Optional.empty();
    }
}
