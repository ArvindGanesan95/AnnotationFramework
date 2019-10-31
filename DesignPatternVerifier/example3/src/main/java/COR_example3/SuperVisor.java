package COR_example3;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler
public class SuperVisor extends Manager {
    private final Logger LOGGER = LoggerFactory.getLogger(SuperVisor.class);

    SuperVisor(Approver approver){
        super(approver);
    }

    @Override
    public void approveRequest(LeaveRequest request) {

        if (request.getDays() <= 5) {
            LOGGER.info("[Handler] Input caught at SuperVisor handler");
            System.out.println("Leave request approved for " + request.getDays() + " days by Supervisor");
        } else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.approveRequest(request);
        }
    }
}