import java.util.ArrayList;
import java.util.Date;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class MiniTest {
    static ArrayList signInTimeRecords=new ArrayList<SignInTimeRecord>();
    static ArrayList signOutTimeRecords=new ArrayList<SignOutTimeRecord>();
    public static void main(String[] args) {
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat image = Imgcodecs.imread("path/to/image.jpg");
        Mat grayImage = new Mat();
        Imgproc.cvtColor(image, grayImage, Imgproc.COLOR_BGR2GRAY);
        CascadeClassifier faceDetector = new CascadeClassifier("path/to/haarcascade_frontalface_default.xml");

        MatOfRect faceDetections = new MatOfRect();
        faceDetector.detectMultiScale(grayImage, faceDetections);

        for (Rect rect : faceDetections.toArray()) {
            Imgproc.rectangle(image, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),
                    new Scalar(0, 255, 0), 3);
        }

        if (faceDetections.toArray().length == 1) {
            boolean isFaceValid = true;

            if (isFaceValid && signInAndOut()) {
                System.out.println("Success!!");
            } else {
                System.out.println("Wrong picture, please take a picture again!!");
            }
        } else {
            System.out.println("Cannot find the picture or more than one picture, please take a picture again!!");
        }
    }
    public static boolean signInAndOut(){
        Date nowTime = new Date();
        int hour = nowTime.getHours();

        for (int i=0;i<signInTimeRecords.size();i++){
            if(signInTimeRecords.get(i)==null){
                signInTimeRecords.add(nowTime);
            }
        }

        for (int i=0;i<signOutTimeRecords.size();i++){
            if (signOutTimeRecords.get(i)==null){
                signOutTimeRecords.add(nowTime);
            }
        }

        if (hour <= 9) {
            System.out.println("Sign in success");
            return true;
        } else if (hour >= 18) {
            System.out.println("Sign out success");
            return true;
        } else {
            System.out.println("Not yet time");
            return false;
        }
    }
}
