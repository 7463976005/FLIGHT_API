package com.FlightAPI.Controller;

import com.FlightAPI.Payload.ReservationDto;
import com.FlightAPI.Payload.ReservationRequest;
import com.FlightAPI.Repository.ReservationRepo;
import com.FlightAPI.Service.ReservationService;
import com.FlightAPI.entity.Reservation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@RestController
@RequestMapping("/api/res")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @Autowired
    private ReservationRepo reservationRepo;


    //http://localhost:8080/api/res/flightId/1/passenger/1/reservation/1
//    @GetMapping("/flightId/{flightId}/passenger/{passengerId}/reservation/{rid}")
//    public ResponseEntity<ReservationDto> saveReservation(@PathVariable("flightId") long flightId,@PathVariable("passengerId") long passengerId,
//                                                         @PathVariable("rid") long rid, @RequestBody ReservationDto reservationDto){
//        ReservationDto dto = reservationService.saveReser(flightId ,passengerId,rid,reservationDto);
//
//        return new ResponseEntity<>(dto, HttpStatus.CREATED);
//    }




    //for saving the passenger and confirm reservation
    //http://localhost:8080/api/res/flightId/1/passenger
    @PostMapping("/flightId/{id}/passenger")
    public Reservation confirmReservation(@PathVariable("id") long id,@RequestBody  ReservationRequest request) {
        Reservation dto = reservationService.bookFlight(id, request);
        // modelMap.addAttribute("reservationId", reservationId.getId());
        return dto;
    }

    //get reservation details by reservation id
    //http://localhost:8080/api/res/reservation/1
    @GetMapping("/reservation/{id}")
    public Reservation findReservation(@PathVariable("id") Long id) {
        Optional<Reservation> findById = reservationRepo.findById(id);
        Reservation reservation = findById.get();

        return reservation;
    }

    //update the reservation details by reservation id
    //http://localhost:8080/api/res/reservation/1
    @PutMapping("/reservation/{id}")
    public Reservation updateReservation(@PathVariable("id") long id, @RequestBody ReservationDto request) {
      //  Optional<Reservation> findById = reservationRepo.findById(request.getReservationId());
        Reservation reservation = reservationRepo.findById(id).orElseThrow(
                () -> new EntityNotFoundException("reservation not found with reservation id" + id)
        );

       //Reservation reservation = findById.get();
        reservation.setCheckedIn(request.isCheckedIn());
        reservation.setNumberOfBags(request.getNumberOfBags());
        return reservationRepo.save(reservation);
    }



}
