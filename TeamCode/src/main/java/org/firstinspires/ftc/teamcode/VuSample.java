package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by luckm on 1/15/2018.
 */
@Autonomous (name = "Vutest", group = "test")
@SuppressWarnings("unused")
public class VuSample extends OpMode {
    private VuforiaTrackable mRelicRecoveryVuMarks;

    private Telemetry.Item visibleVuMarkTelemetry;

    @Override
    public void init() {
        // Find the Android View for Vuforia to display the camera's view.
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "cameraMonitorViewId", "id",
                hardwareMap.appContext.getPackageName());

        // Initialize the Vuforia parameters.
        VuforiaLocalizer.Parameters vuforiaParameters =
                new VuforiaLocalizer.Parameters(cameraMonitorViewId);
        vuforiaParameters.vuforiaLicenseKey = "ARKM5mL/////AAAAGV/QOaj8pk1Zvv/K3obreyhKymFmndGUwHIvBVFc/d+UuEkHUqXeea4MkuAQmnBRlc6POE2HX5yRfdnuGWDFacVJS+ERrcFkMjf9A6zJmE3yy07IyYlrbOD7yzufOQyZPXfyYWOajFu+qkUCsY6vZmt4slwbqTmHmCbsMgww1yJDoTCSKtvc3NHt1auiOybiEWlp+EGdU7JMGpe9SwFYWORnw74B+mRdIECiXZ3JyA/UrXLmvB0E7BG7Kdt6N+gnES0raOK7jyhrrgkV/IngDKo54RxjJ+2SyJXMlL15ecsBvqXbuTe/2ioxJeLMeRRhAAU3QbRkueA1jqAe1lpfCozzDSAZjCGBS8kgZdpR+N8u";

        vuforiaParameters.cameraDirection =
                VuforiaLocalizer.CameraDirection.BACK;
        VuforiaLocalizer vuforiaLocalizer =
                ClassFactory.createVuforiaLocalizer(vuforiaParameters);

        // Load the Relic Recovery VuMarks and activate them.
        VuforiaTrackables vuforiaTrackables =
                vuforiaLocalizer.loadTrackablesFromAsset("RelicVuMark");
        vuforiaTrackables.activate();

        // Save the Relic Recovery VuMarks in an instance variable for access later.
        mRelicRecoveryVuMarks = vuforiaTrackables.get(0);

        // Add a telemetry item so we can set its value efficiently in loop().
        visibleVuMarkTelemetry = telemetry.addData("1. VuMark visible", "(Not Ready)");
    }

    @Override
    public void loop() {
        // Find whatever VuMark trackable is visible to the camera.
        RelicRecoveryVuMark visibleVuMark = RelicRecoveryVuMark.from(mRelicRecoveryVuMarks);

        // Sent a telemetry message with the name of the visible VuMark.
        visibleVuMarkTelemetry.setValue(visibleVuMark);
        telemetry.update();
    }
}