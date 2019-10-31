package COR_example3;

import annotationlibrary.Handler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Handler
public class Manager extends Approver {
    private final Logger LOGGER = LoggerFactory.getLogger(Manager.class);

    Manager(Approver approver){
        super(approver);
    }

    @Override
    public void approveRequest(LeaveRequest request) {
        if (request.getDays() <= 15 && request.getDays() > 5) {
            LOGGER.info("[Handler] Input caught at Manager handler");
            System.out.println("Leave request approved for " + request.getDays() + " days by Manager");
        } else {
            LOGGER.info("[Handler] Cannot process the request. Passing to next handler");
            super.approveRequest(request);
        }
    }

}