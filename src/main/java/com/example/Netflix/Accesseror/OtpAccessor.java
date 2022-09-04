package com.example.Netflix.Accesseror;

import com.example.Netflix.Accesseror.Model.Otp.OtpDto;
import com.example.Netflix.Accesseror.Model.Otp.OtpSentTo;
import com.example.Netflix.Accesseror.Model.Otp.OtpState;
import com.example.Netflix.Exceptions.DependencyFaliureException;
import com.example.Netflix.Exceptions.OtpNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

@Repository
public class OtpAccessor {

    @Autowired
    DataSource dataSource;

    public OtpDto getUnUsedOtp(final String otp, final String userId,final OtpSentTo otpSentTo){
        String query = "SELECT * from otp where userId = ? and otp = ? and state = ? and sentTo=?";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1,userId);
            ps.setString(2,otp);
            ps.setString(3, OtpState.UNUSED.name());
            ps.setString(4, otpSentTo.name());
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                OtpDto otpDto = OtpDto.builder()
                        .otpId(rs.getString(1))
                        .userId(userId)
                        .otp(rs.getString(3))
                        .state(OtpState.valueOf(rs.getString(4)))
                        .createdAt(rs.getDate(5))
                        .sentTo(OtpSentTo.valueOf(rs.getString(6)))
                        .build();
                return otpDto;
            }
            throw new OtpNotFoundException("Otp "+ otp+ " is invalid.");
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

    public void updateOtpState(final String otpId,final OtpState otpState){
        String query = "UPDATE otp set state = ? where otpId = ? ";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, otpState.toString());
            ps.setString(2, otpId);
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

    public void generateOtp(final String userId, final OtpSentTo otpSentTo){
        String query = "INSERT INTO otp (otpId,userId,otp,state,createdAt,sentTo) values(?,?,?,?,?,?) ";

        try(Connection connection = dataSource.getConnection()){
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, UUID.randomUUID().toString());
            ps.setString(2, userId);
            ps.setInt(3,123456);
            ps.setString(4,OtpState.UNUSED.name());
            ps.setDate(5,new Date(System.currentTimeMillis()));
            ps.setString(6,otpSentTo.name());
            ps.execute();

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DependencyFaliureException(e);
        }
    }

}
