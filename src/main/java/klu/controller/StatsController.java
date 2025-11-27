package klu.controller;

import klu.repository.UserRepository;
import klu.repository.SongRepository;
import klu.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/admin/stats")
@CrossOrigin(origins = "http://localhost:5173") // adjust if needed
public class StatsController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SongRepository songRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    // ✅ Endpoint to get overall dashboard stats
    @GetMapping
    public Map<String, Object> getStats() {
        Map<String, Object> stats = new HashMap<>();

        // Total users
        long totalUsers = userRepository.count();

        // Premium users (payments with "SUCCESS" or similar)
        long premiumUsers = paymentRepository.countByPaymentStatus("Paid");

        // Total songs added by admin
        long dbSongs = songRepository.count();

        // Static songs (from frontend - hardcoded number)
        long staticSongs = 20; // or fetch from config

        stats.put("totalUsers", totalUsers);
        stats.put("premiumUsers", premiumUsers);
        stats.put("dbSongs", dbSongs);
        stats.put("staticSongs", staticSongs);

        return stats;
    }
}
