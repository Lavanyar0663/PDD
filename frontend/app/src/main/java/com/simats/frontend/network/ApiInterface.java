package com.simats.frontend.network;

import com.google.gson.JsonObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Header;

public interface ApiInterface {

    // Auth
    @POST("api/auth/login")
    Call<JsonObject> login(@Body JsonObject credentials);

    @POST("api/auth/register")
    Call<JsonObject> registerUser(@Body JsonObject userData);

    @POST("api/auth/forgot-password")
    Call<JsonObject> forgotPassword(@Body JsonObject emailData);

    @POST("api/auth/verify-otp")
    Call<JsonObject> verifyOtp(@Body JsonObject otpData);

    @POST("api/auth/reset-password")
    Call<JsonObject> resetPassword(@Body JsonObject resetData);

    // Patients
    @GET("api/patients")
    Call<List<JsonObject>> getPatients();

    // Prescriptions
    @POST("api/prescriptions/full")
    Call<JsonObject> createFullPrescription(@Body JsonObject prescriptionData);

    @PATCH("api/prescriptions/{id}/dispense")
    Call<JsonObject> dispensePrescription(
            @Path("id") String prescriptionId,
            @Header("Idempotency-Key") String idempotencyKey);

    @GET("api/prescriptions/patient/{id}")
    Call<List<JsonObject>> getPatientPrescriptions(@Path("id") String patientId);

    // New endpoint strictly for Pharmacist dashboard to see all pending
    // prescriptions in system
    @GET("api/prescriptions/pending")
    Call<List<JsonObject>> getPendingPrescriptions();
}
