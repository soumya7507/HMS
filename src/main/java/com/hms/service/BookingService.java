package com.hms.service;

import com.hms.entity.Bookings;
import com.hms.entity.Property;
import com.hms.entity.Rooms;
import com.hms.payload.BookingDto;
import com.hms.repository.BookingsRepository;
import com.hms.repository.PropertyRepository;
import com.hms.repository.RoomsRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService  {

    private final PropertyRepository propertyRepository;
    private final RoomsRepository roomsRepository;
    private final BookingsRepository bookingsRepository;
    private PdfService pdfService;
    private TwilioService twilioService;
    private WhatsAppService whatsAppService;
    private EmailService emailService;
    @Value("${phone.number}")
    private String fromNumber;

    public BookingService(PropertyRepository propertyRepository,
                          RoomsRepository roomsRepository,
                          BookingsRepository bookingsRepository, PdfService pdfService, TwilioService twilioService, WhatsAppService whatsAppService, EmailService emailService) {
        this.propertyRepository = propertyRepository;
        this.roomsRepository = roomsRepository;
        this.bookingsRepository = bookingsRepository;
        this.pdfService = pdfService;
        this.twilioService = twilioService;
        this.whatsAppService = whatsAppService;
        this.emailService = emailService;
    }


    public String createBooking(long propertyId, BookingDto bookingsDto, String type) {
        try {
            Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new RuntimeException("Could not find Property"));
            List<Rooms> rooms = roomsRepository.findAvailableRooms(property, type, bookingsDto.getCheckInDate(), bookingsDto.getCheckOutDate());
            if(rooms.isEmpty()){
                return "Rooms Not Available";
            }

            for (Rooms room : rooms) {
                if (room.getCount() == 0) {
                    return "Rooms not available on: " + room.getDate();
                }
            }
            double basePrice = 0;
            for (Rooms room : rooms) {
                basePrice = room.getPerNightPrice() * (double) (rooms.size() - 1);
            }
            double gst = (basePrice * 18) / 100;
            double totalPrice = basePrice + gst;
            Bookings bookings = mapToEntity(bookingsDto);
            bookings.setBasePrice(basePrice);
            bookings.setTotalGST(gst);
            bookings.setTotalPrice(totalPrice);
            bookings.setProperty(property);

            Bookings savedBooking = bookingsRepository.save(bookings);
            if (savedBooking != null) {
                for (Rooms room : rooms) {
                    if(room.getDate().equals(bookings.getCheckOutDate())){
                        break;
                    }
                    else {
                        room.setCount(room.getCount() - 1);
                        roomsRepository.save(room);
                    }

                }
            }
            pdfService.generateBookingPdf("G:\\hms_bookings\\confirmation-order" + savedBooking.getId() + ".pdf", property, savedBooking);
            emailService.senBookingEmail("G:\\hms_bookings\\confirmation-order" + savedBooking.getId() + ".pdf","bratsaty44@gmail.com","jsdfj","ehbdh");
            twilioService.sendSms("+917608825266", "Booking Confirmed. Your booking id is: " + bookings.getId());
            whatsAppService.sendWhatsAppMessage("+917608825266", "Booking Confirmed. Your booking id is: " + bookings.getId());
            return "Booking Created Successfully";
        } catch (Exception e) {
            e.printStackTrace();
        }return "Booking Failed";
    }

    Bookings mapToEntity(BookingDto bookingDto) {
        Bookings bookings = new Bookings();
        bookings.setName(bookingDto.getName());
        bookings.setEmailId(bookingDto.getEmailId());
        bookings.setCheckInDate(bookingDto.getCheckInDate());
        bookings.setCheckOutDate(bookingDto.getCheckOutDate());
        return bookings;

    }

    static class SMSOTPData {
        private final String smsOtp;
        private final long expiryTime;
        public SMSOTPData(String smsOtp, long expiryTime) {
            this.smsOtp = smsOtp;
            this.expiryTime = expiryTime;
        }
        public String getOTP() {
            return smsOtp;
        }
        public long getExpiryTime() {
            return expiryTime;
        }
    }
}
