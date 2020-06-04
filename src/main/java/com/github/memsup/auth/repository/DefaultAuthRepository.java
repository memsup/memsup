package com.github.memsup.auth.repository;

import com.github.memsup.auth.domain.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

@Repository
@Slf4j
@RequiredArgsConstructor
public final class DefaultAuthRepository implements AuthUserRepository {

    private final DataSource dataSource;
    private final PasswordEncoder passwordEncoder;

    @Override
    public final Optional<AuthUser> findByUsernameOrEmail(final String username) {

        log.info("girdi");
        AuthUser authUser = null;
        try (Connection connection =
                     dataSource.getConnection()) {

            connection.setAutoCommit(false);

            final String selectSection = "select auth_user_id,auth_user_name, auth_user_pwd, auth_user_email from";
            final String whereSection = "auth_user where auth_user_name = ? or auth_user_email = ?";
            final String searchAuthUserQuery = String.format("%s %s", selectSection, whereSection);
            final int inSensitive = ResultSet.TYPE_SCROLL_INSENSITIVE;
            final int readOnly = ResultSet.CONCUR_READ_ONLY;
            final int closeCursorsAtCommit = ResultSet.CLOSE_CURSORS_AT_COMMIT;

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(searchAuthUserQuery, inSensitive, readOnly, closeCursorsAtCommit)) {

                preparedStatement.setString(1, username);
                preparedStatement.setString(2, username);

                final ResultSet rs = preparedStatement.executeQuery();
                rs.setFetchSize(1);

                final boolean isUserExist = rs.first();
                if (isUserExist) {
                    authUser = new AuthUser();
                    authUser.setAuthUserId(rs.getInt(1));
                    authUser.setAuthUsername(rs.getString(2));
                    authUser.setAuthPassword(rs.getString(3));
                    authUser.setAuthUserEmail(rs.getString(4));
                }

                rs.close();

            } catch (SQLException e) {
                log.error(e.getMessage());
                connection.rollback();
            }

            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return Optional.ofNullable(authUser);
    }

    @Override
    public final boolean register(final AuthUser authUser) {

        boolean isRegistered = false;

        try (Connection connection =
                     dataSource.getConnection()) {
            connection.setAutoCommit(false);

            final String insertQuery = "insert into auth_user(auth_user_name,auth_user_pwd,auth_user_email) values (?,?,?)";

            try (PreparedStatement preparedStatement =
                         connection.prepareStatement(insertQuery)) {

                final String encodedPassword = passwordEncoder.encode(authUser.getAuthPassword());

                preparedStatement.setString(1, authUser.getAuthUsername());
                preparedStatement.setString(2, encodedPassword);
                preparedStatement.setString(3, authUser.getAuthUserEmail());

                final int processResult = preparedStatement.executeUpdate();
                isRegistered = processResult == 1;

            } catch (SQLException e) {
                log.error(e.getMessage());
                connection.rollback();
            }

            connection.commit();

        } catch (SQLException e) {
            log.error(e.getMessage());
        }

        return isRegistered;
    }
}