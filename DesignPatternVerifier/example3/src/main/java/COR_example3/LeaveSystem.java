package COR_example3;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LeaveSystem {
    private static final Logger LOGGER = LoggerFactory.getLogger(LeaveSystem.class);

    public static void main(String[] args) {

        LOGGER.info("Creating Chain of handlers");
        Approver supervisor = new SuperVisor(new Manager(new AccountHead(null)));
        LOGGER.info("Passing requests...");
        supervisor.approveRequest(new LeaveRequest(4));
        supervisor.approveRequest(new LeaveRequest(40));
        supervisor.approveRequest(new LeaveRequest(12));

    }

}