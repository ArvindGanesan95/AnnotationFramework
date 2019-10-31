package COR_example3;

import annotationlibrary.AbstractHandle;
import annotationlibrary.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@AbstractHandler
public abstract class Approver {
    private final Logger LOGGER = LoggerFactory.getLogger(Approver.class);

    protected Approver nextApprover;

    @AbstractHandle
    public void approveRequest(LeaveRequest request){
        // If there is a next logger to handle the request, pass the request to it
        if(nextApprover!=null) {
            LOGGER.info("[Passing to next handler]");
            nextApprover.approveRequest(request);
        }
        else {
            LOGGER.info("No handler can process the request.");
            System.out.println("Error: No handler can process the leave request");
        }
    }
      Approver(Approver approver){
        this.nextApprover = approver;
    }
}