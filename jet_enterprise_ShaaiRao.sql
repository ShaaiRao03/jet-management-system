SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `jet_enterprise`
--

-- --------------------------------------------------------

--
-- Table structure for table `admin`
--

CREATE TABLE `admin` (
  `userName` varchar(10) NOT NULL,
  `salary` int(11) NOT NULL,
  `joinDate` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `admin`
--

INSERT INTO `admin` (`userName`, `salary`, `joinDate`) VALUES
('a', 1000000, '2023-04-01');

-- --------------------------------------------------------

--
-- Table structure for table `airframe`
--

CREATE TABLE `airframe` (
  `jetID` varchar(15) NOT NULL,
  `timeSinceNew` int(11) NOT NULL,
  `landings` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `airframe`
--

INSERT INTO `airframe` (`jetID`, `timeSinceNew`, `landings`) VALUES
('14501100', 4458, 3074),
('14501142', 2901, 512),
('14501199', 200, 90),
('20370', 2636, 2399),
('20556', 1851, 911),
('469', 866, 499),
('50000306', 3328, 2748),
('50500329', 3080, 3819),
('525B-0146', 3257, 2213),
('533', 7428, 2787),
('5565', 6765, 3333),
('560XL-5026', 3341, 2914),
('5737', 8025, 4416),
('60016', 1727, 741),
('6073', 1658, 648),
('6162', 952, 548),
('70094', 296, 143),
('700989', 740, 274),
('73017', 252, 94),
('9023', 7568, 2510),
('9254', 2810, 1132),
('9302', 3182, 954),
('9491', 2068, 703),
('9679', 3954, 1274),
('FL-340', 4823, 4645),
('HB-0024', 4053, 2241),
('TH-2177', 2283, 2000);

-- --------------------------------------------------------

--
-- Table structure for table `airports`
--

CREATE TABLE `airports` (
  `isoCode` varchar(4) DEFAULT NULL,
  `airportCode` varchar(4) NOT NULL,
  `airportName` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `airports`
--

INSERT INTO `airports` (`isoCode`, `airportCode`, `airportName`) VALUES
('MYS', ' KUL', 'Kuala Lumpur International Airport'),
('AUS', 'BNE', 'Brisbane Airport'),
('AUS', 'MBE', 'Melbourne Airport'),
('MYS', 'PEN', 'Penang International Airport'),
('SGP', 'SIN', 'Changi Airport'),
('SGP', 'XSP', 'Seletar Airport');

-- --------------------------------------------------------

--
-- Table structure for table `cart`
--

CREATE TABLE `cart` (
  `userName` varchar(10) DEFAULT NULL,
  `cartID` int(11) NOT NULL,
  `jetID` varchar(15) DEFAULT NULL,
  `jetName` varchar(100) DEFAULT NULL,
  `status` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `cart`
--

INSERT INTO `cart` (`userName`, `cartID`, `jetID`, `jetName`, `status`) VALUES
('s1', 38, '560XL-5026', '1999 Cessna Citation Excel', 'Approved'),
('s1', 41, '9023', '2000 Bombardier Global Express', 'Rejected'),
('s1', 49, '533', '1997 Gulfstream GV', 'Approved'),
('s1', 54, '20556', '2015 Bombardier Challenger 350', 'Rejected'),
('s1', 61, '14501100', '2009 Embraer Legacy 600', 'Pending'),
('s1', 74, '14501100', '2009 Embraer Legacy 600', 'Pending'),
('d1', 77, '14501100', '2009 Embraer Legacy 600', 'Rejected'),
('d1', 79, '20556', '2015 Bombardier Challenger 350', 'Approved'),
('th', 81, '20370', '2012 Bombardier Challenger 300', 'Under Consideration');

-- --------------------------------------------------------

--
-- Table structure for table `client`
--

CREATE TABLE `client` (
  `userName` varchar(10) NOT NULL,
  `job` varchar(20) NOT NULL,
  `company` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `client`
--

INSERT INTO `client` (`userName`, `job`, `company`) VALUES
('a', 'Administrator', 'Jet Craft'),
('d1', 'Director', 'Dini Veterinary Grou'),
('s1', 'CEO', 'Google'),
('test', 'Test', 'Test'),
('th', 'CEO', 'Accenture');

-- --------------------------------------------------------

--
-- Table structure for table `countrydelivery`
--

CREATE TABLE `countrydelivery` (
  `isoCode` varchar(4) NOT NULL,
  `countryName` varchar(30) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `countrydelivery`
--

INSERT INTO `countrydelivery` (`isoCode`, `countryName`) VALUES
('AUS', 'Australia'),
('MYS', 'Malaysia'),
('SGP', 'Singapore');

-- --------------------------------------------------------

--
-- Table structure for table `engine`
--

CREATE TABLE `engine` (
  `jetID` varchar(15) NOT NULL,
  `hoursSinceNew` int(11) NOT NULL,
  `cyclesSinceNew` int(11) NOT NULL,
  `rightEngineID` varchar(15) NOT NULL,
  `leftEngineID` varchar(15) NOT NULL,
  `engineDescription` varchar(60) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `engine`
--

INSERT INTO `engine` (`jetID`, `hoursSinceNew`, `cyclesSinceNew`, `rightEngineID`, `leftEngineID`, `engineDescription`) VALUES
('14501100', 4458, 3074, 'CAE313172', 'CAE313176', 'Rolls-Royce AE 3007A1E'),
('14501142', 2901, 1512, 'CAE-313275', 'CAE-313274', 'Rolls-Royce AE 3007A2'),
('14501199', 3954, 1123, 'S-4839', '12617', 'Honeywell AS907-2-1A'),
('20370', 2651, 2403, 'P118900', 'P118901', 'Honeywell AS907-1-1A'),
('20556', 1851, 928, 'P136226', 'P136227', 'Honeywell AS907-2-1A'),
('469', 3456, 1100, '121869', '21323', 'Rolls-Royce BR700-710A2-20'),
('50000306', 3328, 2748, 'PCE-LC0589', 'PCE-LC0584', 'Pratt  Whitney PW617F-E'),
('50500329', 3080, 3819, 'PCE-DG0633', 'PCE-DG0617', 'Pratt & Whitney PW535E'),
('525B-0146', 3218, 2185, '141300', '141301', 'Williams International FJ44-3A'),
('533', 7388, 2768, '11172', '11171', 'Rolls-Royce BR700-710A1-10'),
('5565', 6765, 3333, '873821', '873823', 'General Electric CF34-3B'),
('560XL-5026', 2729, 2500, 'S-4831', 'Z-958939', 'Pratt & Whitney Canada PW545A'),
('5737', 8025, 4426, '950634', '950633', 'General Electric CF34-3B'),
('60016', 1727, 751, '56012', '56013', 'Rolls-Royce BR700-710D5-21'),
('6073', 1658, 648, '25260', '25261', 'Rolls-Royce BR725A1-12'),
('6162', 885, 515, '801629', '801627', 'General Electric CF-34-3B'),
('70094', 296, 150, '904306', '904307', 'General Electric Passport 20-19BB1A'),
('700989', 740, 274, 'PCE-DK0018', 'PCE-DK0017', 'PRATT & WHITNEY PW535E'),
('73017', 252, 94, 'PCE-GA0151', 'PCE-GA0144', 'Pratt & Whitney PW815GA'),
('9023', 7566, 2490, '12156', '12149', 'Rolls Royce BR700-710A2-20'),
('9254', 2810, 1132, '12618', '12617', 'Rolls Royce BR700-710A2-20'),
('9302', 3954, 1275, '12718', '12719', 'Rolls-Royce BR700-710A2-20'),
('9491', 2068, 707, '22108', '22109', 'Rolls Royce BR700-710A2-20'),
('9679', 3182, 958, '22489', '22487', 'Rolls-Royce BR725A1-11'),
('FL-340', 4761, 4469, 'PCE-PK427', 'PCE-PK428', 'Pratt Whitney PT6A-60A'),
('HB-0024', 4053, 2241, 'P-129442', 'P-129445', 'Honeywell TFE731-5BR-1H'),
('TH-2177', 2283, 808, '1031275', '1031274', 'Continental IO-550-C49B');

-- --------------------------------------------------------

--
-- Table structure for table `exterior`
--

CREATE TABLE `exterior` (
  `jetID` varchar(15) NOT NULL,
  `stripe` varchar(30) NOT NULL,
  `basepaint` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `exterior`
--

INSERT INTO `exterior` (`jetID`, `stripe`, `basepaint`) VALUES
('14501100', 'Black, Grey, Blue', 'White'),
('14501142', 'Beige and Gray', 'Matterhorn White'),
('14501199', 'Beige and Gray', 'Matterhorn White and Dark Blue'),
('20370', 'Black & Red', 'Matterhorn White'),
('20556', 'Beige and Gray', 'Matterhorn White and Dark Blue'),
('469', 'Beige and Gray', 'White'),
('50000306', 'Red & Black', 'Matterhorn White'),
('50500329', 'Blue', 'White'),
('525B-0146', 'Blue, Light Blue and Grey', 'Grey'),
('533', 'Red & Black', 'White'),
('5565', 'Yellow', 'Matterhorn White and Dark Blue'),
('560XL-5026', 'Beige and Gray', 'Matterhorn White'),
('5737', 'Blue and Red', 'White'),
('60016', 'Blue, Light Blue and Grey', 'Matterhorn White'),
('6073', 'Black, Grey, Silver', 'Matterhorn White'),
('6162', 'Black, Grey, Silver', 'Matterhorn White'),
('70094', 'Red', 'Grey'),
('700989', 'Blue and Red', 'Matterhorn White'),
('73017', 'Blue, Light Blue and Grey', 'Matterhorn White'),
('9023', 'Red', 'Matterhorn White'),
('9254', 'Beige and Gray', 'Overall White'),
('9302', 'Green and Gold', 'Matterhorn White'),
('9491', 'Pastel Purple & Medium Gray', 'Matterhorn White'),
('9679', 'Beige and Gray', 'Matterhorn White'),
('FL-340', 'Black & Red', 'White'),
('HB-0024', 'Beige and Gray', 'Matterhorn White and Dark Blue'),
('TH-2177', 'Blue and Red', 'White');

-- --------------------------------------------------------

--
-- Table structure for table `interior`
--

CREATE TABLE `interior` (
  `jetID` varchar(15) NOT NULL,
  `forwardCabinConfig` varchar(100) NOT NULL,
  `AftCabinConfig` varchar(100) NOT NULL,
  `numPassengers` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `interior`
--

INSERT INTO `interior` (`jetID`, `forwardCabinConfig`, `AftCabinConfig`, `numPassengers`) VALUES
('14501100', 'Four (4) executive club seats with foldout tables', 'Two (2) executive club seats opposite Three (3) place Berthable Divan', 14),
('14501142', 'Four (4) Place Double Club', 'Two (2) Place Executive Club with pullout table opposite a three (3) Place Divan', 15),
('14501199', 'Double (2) Club', 'Two (2) Place Club Opposite a Three (3) Place Divan', 12),
('20370', 'Double (2) Club', 'Two (2) executive club seats opposite Three (3) place Berthable Divan', 4),
('20556', 'Double (2) Club', 'Four (4) Place Club', 12),
('469', 'Double (2) Club', 'Two (2) Three (3) Place Divan', 14),
('50000306', 'Four (4) Place Club setting with pull out tables', 'Four (4) Place Club', 4),
('50500329', 'Two (2) Place Fwd Side-Facing Divan', 'Two (2) Place Fwd Facing Seats', 8),
('525B-0146', 'Four (4) Place Club', 'Two (2) Place Club opposite Three (3) Place Divan', 14),
('533', '4 Single Club Seats', '2 Four Place facing Divans', 16),
('5565', 'Four (4) Place Club', 'Two (2) Place Club opposite Three (3) Place Divan', 9),
('560XL-5026', 'Double (2) Club', 'Two (2) Place Fwd Facing Seats', 4),
('5737', 'Four (4) Place Club', 'Two (2) Place Club Opposite Four (4) Place Divan', 10),
('60016', 'Four (4) Place Executive Club with pullout table', 'Two (2) Place Executive Club with pullout table opposite a three (3) Place Divan', 13),
('6073', 'Four (4) Place Club', 'Three (3) Place Divan opposite a Three (3) Place Divan', 14),
('6162', '4 Single Club Seats', '2 Four Place facing Divans', 17),
('70094', 'Four (4) Place Club', 'Two (2) Place Club opposite Three (3) Place Divan', 16),
('700989', 'Four (4) Place Club', 'Two (2) Place Forward-Facing Club', 8),
('73017', 'Four (4) Place Double Club', 'Two (2) Place Club Opposite a Three (3) Place Divan', 15),
('9023', 'Four (4) Place Double Club', 'Two (2) Place Club Opposite Three (3) Place 9G Divan', 12),
('9254', 'Double (2) Club', 'Two (2) Place Club Opposite Three (3) Place 9G Divan', 13),
('9302', 'Four (4) Place Double Club', 'Two (2) Place Club Opposite a Three (3) Place Divan', 13),
('9491', 'Four (4) Place Club', 'Two (2) Place Forward-Facing Club', 13),
('9679', 'Four (4) Place Executive Club with pullout table', 'Two (2) Place Executive Club with pullout table opposite a three (3) Place Divan', 15),
('FL-340', 'Four (4) Place Club', 'Four (4) Place Club', 8),
('HB-0024', 'Double (2) Club', 'Four (4) Place Club', 4),
('TH-2177', 'Four (4) Place Club', 'Two (2) Place Forward-Facing Club', 6);

-- --------------------------------------------------------

--
-- Table structure for table `logindetailsjet`
--

CREATE TABLE `logindetailsjet` (
  `userName` varchar(10) NOT NULL,
  `password` varchar(10) NOT NULL,
  `security_ques` varchar(50) NOT NULL,
  `answer` varchar(20) NOT NULL,
  `role` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `logindetailsjet`
--

INSERT INTO `logindetailsjet` (`userName`, `password`, `security_ques`, `answer`, `role`) VALUES
('a', 'a1', 'Who is your favourite actor ?\r\n', 'Jayam', 'Admin'),
('d1', 'd1', 'Who is your favourite actor ?', 'Vijay', 'Client'),
('s1', 's1', 'Who is your favourite actor ?', 'Vijay', 'Client'),
('test', '1', 'Who is your favourite actor ?', 'Test', 'Client'),
('th', 't1', 'What is your favourite food ?', 'Chicken chop', 'Client');

-- --------------------------------------------------------

--
-- Table structure for table `manufacturer`
--

CREATE TABLE `manufacturer` (
  `manufacturerID` int(11) NOT NULL,
  `manufacturerName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `manufacturer`
--

INSERT INTO `manufacturer` (`manufacturerID`, `manufacturerName`) VALUES
(1, 'Bombardier'),
(2, 'Cessna'),
(3, 'Dassault'),
(4, 'Beechcraft'),
(5, 'Gulfstream'),
(6, 'Embraer');

-- --------------------------------------------------------

--
-- Table structure for table `model`
--

CREATE TABLE `model` (
  `manufacturerID` int(11) NOT NULL,
  `modeLID` int(11) NOT NULL,
  `modelName` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `model`
--

INSERT INTO `model` (`manufacturerID`, `modeLID`, `modelName`) VALUES
(1, 1, 'Challenger 300'),
(1, 2, 'Challenger 350'),
(1, 3, 'Challenger 604'),
(1, 4, 'Challenger 605'),
(1, 5, 'Challenger 650'),
(2, 6, 'Citation CJ3'),
(2, 7, 'Citation Excel'),
(3, 8, 'Falcon 8X'),
(4, 9, 'G58 Baron'),
(5, 10, 'G600'),
(5, 11, 'G650'),
(1, 12, 'Global 5000'),
(1, 14, 'Global 6000'),
(1, 15, 'Global 6500'),
(1, 16, 'Global 7500'),
(1, 17, 'Global Express'),
(1, 18, 'Global XRS'),
(5, 19, 'GV'),
(4, 20, 'Hawker 750'),
(4, 21, 'King Air 350'),
(4, 22, 'King Air 360'),
(6, 23, 'Legacy 600'),
(6, 24, 'Legacy 650'),
(6, 25, 'Phenom 100'),
(6, 26, 'Phenom 300'),
(6, 27, 'Phenom 300 E');

-- --------------------------------------------------------

--
-- Table structure for table `paymentmethod`
--

CREATE TABLE `paymentmethod` (
  `methodID` int(11) NOT NULL,
  `methodName` varchar(40) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `paymentmethod`
--

INSERT INTO `paymentmethod` (`methodID`, `methodName`) VALUES
(1, 'Cheque'),
(2, 'Financing'),
(3, 'Bank Transfer');

-- --------------------------------------------------------

--
-- Table structure for table `privatejet`
--

CREATE TABLE `privatejet` (
  `modeLID` int(11) NOT NULL,
  `jetID` varchar(15) NOT NULL,
  `year` int(11) NOT NULL,
  `highlight1` varchar(40) NOT NULL,
  `highlight2` varchar(40) NOT NULL,
  `image` varchar(50) NOT NULL,
  `price` bigint(20) NOT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `privatejet`
--

INSERT INTO `privatejet` (`modeLID`, `jetID`, `year`, `highlight1`, `highlight2`, `image`, `price`, `quantity`) VALUES
(23, '14501100', 2009, 'Fully Programmed', 'Recent Paint and Interior', 'jet_Embraer600.jpg', 1000000, 2),
(24, '14501142', 2011, 'Engines Enrolled on RRCC', 'Fresh 96 Month Inspection', 'jet_Embraer650.jpg', 2000000, 1),
(22, '14501199', 2022, 'Collins Proline Fusion', 'Nine (9) Passenger Configuration', 'jet_BeechcraftKingAir360.jpg', 3000000, 4),
(1, '20370', 2012, 'Enrolled on Engine & APU Programs', 'Proline 21 Advanced Upgrade', 'jet_BombardierChallenger300.jpg', 14000000, 3),
(2, '20556', 2015, 'Engines & APU Enrolled on MSP', 'Eight (8) Passenger Configuration', 'jet_BombardierChallenger350.jpg', 6000000, 2),
(8, '469', 2021, 'Fully Enrolled on Programs', 'HUD, EVS, SVS & FANS 1/A+', 'jet_DassanFalcon8X.jpg', 6000000, 1),
(25, '50000306', 2013, 'Engines Enrolled on ESP Gold', 'No Accident or Incident History', 'jet_EmbraerPhenom100.jpg', 6000000, 2),
(26, '50500329', 2015, 'G3000 Avionics', 'FDR', 'jet_EmbraerPhenom300.jpg', 9500000, 3),
(6, '525B-0146', 2007, 'EU OPS', 'Engines Enrolled on Programs', 'jet_CessnaCitationCJ3.jpg', 9500000, 5),
(19, '533', 1997, 'DU-885 Plane Deck', 'FANS 1/A (AFN / CPDLC / ADB-C)', 'jet_GulfstreamGV.jpg', 9500000, 3),
(3, '5565', 2003, 'Engines & APU Enrolled on Programs', 'Available for viewing in Nuremberg', 'jet_BombardierChallenger604.jpg', 1000000, 5),
(7, '560XL-5026', 1999, 'Engines on ESP Gold', 'APU on JSSI', 'jet_CessnaCitationExcel.jpg', 1000000, 2),
(20, '5737', 2008, 'Engines Enrolled on GE OnPoint', 'APU Enrolled on Honeywell MSP', 'jet_BombardierChallenger605.jpg', 12500000, 5),
(15, '60016', 2019, 'New Paint 2022 BAS Tucson', '7500 Interior Styling', 'jet_BombardierChallenger6500.jpg', 12500000, 1),
(11, '6073', 2014, 'Engines and APU on JSSI', 'Fresh 96-Month Inspection', 'jet_GulfstreamG650.jpg', 1000000, 3),
(5, '6162', 2021, 'Fully Enrolled on Programs', 'Synthetic Vision System', 'jet_BombardierChallenger650.jpg', 1200000, 6),
(16, '70094', 2022, 'Able to be converted to a Global 8000', 'KA BAND', 'jet_BombardierGlobal7500.jpg', 900000, 5),
(27, '700989', 2020, 'G3000 Avionics Suite', 'Engine Coverage and SP Gold Lite', 'jet_EmbraerPhenom300E.jpg', 1000000, 4),
(10, '73017', 2020, 'Predictive Windshear', 'Situation Awareness Package', 'jet_GulfstreamG600.jpg', 5000000, 5),
(17, '9023', 2000, 'Fully Enrolled on Programs', 'Cabin Refurbished in 2021', 'jet_BombardierGlobalExpress.jpg', 920000, 2),
(18, '9254', 2008, '180 Month Completed at Duncan Aviation', 'Ka-Band Internet', 'jet_BombardierGlobalXRS.jpg', 1000000, 3),
(12, '9302', 2009, 'Engines on RRCC, APU on MSP', 'Airframe on Bombardier Smartparts', 'jet_BombardierGlobal50002009.jpg', 19000000, 1),
(14, '9491', 2013, 'Fully Enrolled on Programs', '120 Month Inspection Currently Underway', 'jet_BombardierGlobal6000.jpg', 9300000, 6),
(12, '9679', 2015, 'Inviting best Offers', 'Motivated seller', 'jet_BombardierGlobal5000.jpg', 8000000, 3),
(21, 'FL-340', 2002, 'G1000 Avionics Upgrade', '8+1 Passenger Configuration', 'jet_BeechcraftKingAir350.jpg', 10000000, 4),
(20, 'HB-0024', 2008, 'Engines & APU Enrolled on Program', '9 Passengers Configuration', 'jet_BeechcraftHawker750.jpg', 6000000, 2),
(9, 'TH-2177', 2007, 'De-Ice Boots', 'Garmin G1000 Avionics Suite', 'jet_BeechcraftG58Baron.jpg', 3000000, 4);

-- --------------------------------------------------------

--
-- Table structure for table `submission`
--

CREATE TABLE `submission` (
  `submissionID` int(11) NOT NULL,
  `jetQuantity` int(11) DEFAULT NULL,
  `additionalFeatures` varchar(101) DEFAULT NULL,
  `totalAmount` bigint(20) DEFAULT NULL,
  `paymentmethod` int(11) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `deliveryCountry` varchar(4) DEFAULT NULL,
  `deliveryLocation` varchar(4) DEFAULT NULL,
  `cartID` int(11) DEFAULT NULL,
  `submissionDate` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `submission`
--

INSERT INTO `submission` (`submissionID`, `jetQuantity`, `additionalFeatures`, `totalAmount`, `paymentmethod`, `bankName`, `deliveryCountry`, `deliveryLocation`, `cartID`, `submissionDate`) VALUES
(20, 2, 'S', 19000000, 2, 'Swiss Bank', 'MYS', ' KUL', 49, '2023-05-06'),
(39, 1, 's', 1000000, 2, 's', 'MYS', ' KUL', 77, '2023-05-13'),
(40, 2, 'Extra equipment needed', 12000000, 2, 'Maybank', 'MYS', 'PEN', 79, '2023-05-13'),
(42, 2, 'None', 28000000, 2, 'Maybank', 'SGP', 'SIN', 81, '2023-05-13');

-- --------------------------------------------------------

--
-- Table structure for table `transaction`
--

CREATE TABLE `transaction` (
  `transactionID` int(11) NOT NULL,
  `jetQuantity` int(11) DEFAULT NULL,
  `additionalFeatures` varchar(101) DEFAULT NULL,
  `totalAmount` bigint(20) DEFAULT NULL,
  `paymentmethod` int(11) DEFAULT NULL,
  `bankName` varchar(50) DEFAULT NULL,
  `deliveryCountry` varchar(4) DEFAULT NULL,
  `deliveryLocation` varchar(4) DEFAULT NULL,
  `jetID` varchar(15) DEFAULT NULL,
  `transactionDate` date DEFAULT NULL,
  `userName` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `transaction`
--

INSERT INTO `transaction` (`transactionID`, `jetQuantity`, `additionalFeatures`, `totalAmount`, `paymentmethod`, `bankName`, `deliveryCountry`, `deliveryLocation`, `jetID`, `transactionDate`, `userName`) VALUES
(2, 3, 'Change seats to black colour\r\n', 3000000, 3, 'Maybank', 'MYS', ' KUL', '5565', '2023-04-26', 's1'),
(5, 2, 'Blue colour base paint\r\n', 28000000, 1, 'ADC', 'AUS', 'MBE', '20370', '2021-04-27', 's1'),
(6, 2, 'White base paint', 28000000, 1, 'A', 'MYS', ' KUL', '20370', '2021-04-20', 's1'),
(7, 2, 'Need curtains for the panes', 28000000, 1, 'A', 'MYS', ' KUL', '20370', '2021-05-26', 's1'),
(8, 2, 'Change the pilot seat to black ', 28000000, 1, 'ADC', 'AUS', 'MBE', '20370', '2022-07-26', 's1'),
(9, 2, 'Need extra tyres', 28000000, 1, 'ADC', 'AUS', 'MBE', '20370', '2022-12-26', 's1'),
(10, 2, 'Blue base paint', 28000000, 1, 'A', 'MYS', ' KUL', '20370', '2022-12-26', 's1'),
(11, 4, 'Large lightning sticker on the body of the jet', 50000000, 2, 'sdcsd', 'MYS', 'PEN', '5737', '2023-05-04', 'd1'),
(12, 2, 'Black stripe', 2000000, 1, 'S', 'MYS', ' KUL', '14501100', '2023-05-04', 'd1'),
(13, 2, 'White Stripe', 2000000, 2, 'S', 'MYS', ' KUL', '560XL-5026', '2023-05-09', 'd1'),
(14, 1, 'White stripe', 12500000, 2, 'AS', 'AUS', 'MBE', '60016', '2023-05-12', 'd1'),
(15, 2, 'S', 19000000, 2, 'Swiss Bank', 'MYS', ' KUL', '533', '2023-05-13', 's1'),
(16, 2, 'Extra equipment needed', 12000000, 2, 'Maybank', 'MYS', 'PEN', '20556', '2023-05-13', 'd1'),
(17, 2, 'None', 28000000, 1, 'Maybank', 'SGP', 'SIN', '20370', '2021-07-13', 'th');

-- --------------------------------------------------------

--
-- Table structure for table `userjet`
--

CREATE TABLE `userjet` (
  `userName` varchar(10) NOT NULL,
  `identity_card` varchar(30) NOT NULL,
  `first_name` varchar(10) NOT NULL,
  `last_name` varchar(10) DEFAULT NULL,
  `email` varchar(30) NOT NULL,
  `phoneNum` varchar(20) NOT NULL,
  `address` varchar(150) NOT NULL,
  `country` varchar(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `userjet`
--

INSERT INTO `userjet` (`userName`, `identity_card`, `first_name`, `last_name`, `email`, `phoneNum`, `address`, `country`) VALUES
('a', '031203040034', 'Shadvikk', 'Rao', 'shadvik@yahoo.com', '0163719271', 'Taman Setia Jaya', 'Malaysia'),
('d1', '021225013993', 'Dini', 'Shaa', 'dini@gmail.com', '0178463782', 'Tmn Permas Jaya', 'India'),
('s1', '030312050021', 'Shaai', 'Rao', 'shaairao@yahoo.com', '0176359916', 'Taman Setia Indah', 'Malaysia'),
('test', '1', 'Test', 'Test', 'Test', '1', 'Test', 'Malaysia'),
('th', '090912093212', 'Thiva', 'Shinie', 'thiva@gmail.com', '0189281213', 'Taman Setia Indah', 'USA');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `admin`
--
ALTER TABLE `admin`
  ADD PRIMARY KEY (`userName`);

--
-- Indexes for table `airframe`
--
ALTER TABLE `airframe`
  ADD PRIMARY KEY (`jetID`);

--
-- Indexes for table `airports`
--
ALTER TABLE `airports`
  ADD PRIMARY KEY (`airportCode`);

--
-- Indexes for table `cart`
--
ALTER TABLE `cart`
  ADD PRIMARY KEY (`cartID`),
  ADD KEY `user_cart` (`userName`),
  ADD KEY `jet_cart` (`jetID`);

--
-- Indexes for table `client`
--
ALTER TABLE `client`
  ADD PRIMARY KEY (`userName`);

--
-- Indexes for table `countrydelivery`
--
ALTER TABLE `countrydelivery`
  ADD PRIMARY KEY (`isoCode`);

--
-- Indexes for table `engine`
--
ALTER TABLE `engine`
  ADD PRIMARY KEY (`jetID`);

--
-- Indexes for table `exterior`
--
ALTER TABLE `exterior`
  ADD PRIMARY KEY (`jetID`);

--
-- Indexes for table `interior`
--
ALTER TABLE `interior`
  ADD PRIMARY KEY (`jetID`);

--
-- Indexes for table `logindetailsjet`
--
ALTER TABLE `logindetailsjet`
  ADD PRIMARY KEY (`userName`);

--
-- Indexes for table `manufacturer`
--
ALTER TABLE `manufacturer`
  ADD PRIMARY KEY (`manufacturerID`);

--
-- Indexes for table `model`
--
ALTER TABLE `model`
  ADD PRIMARY KEY (`modeLID`),
  ADD KEY `manufacturer_model` (`manufacturerID`);

--
-- Indexes for table `paymentmethod`
--
ALTER TABLE `paymentmethod`
  ADD PRIMARY KEY (`methodID`);

--
-- Indexes for table `privatejet`
--
ALTER TABLE `privatejet`
  ADD PRIMARY KEY (`jetID`),
  ADD KEY `privatejet_model` (`modeLID`);

--
-- Indexes for table `submission`
--
ALTER TABLE `submission`
  ADD PRIMARY KEY (`submissionID`),
  ADD KEY `submission_cart` (`cartID`),
  ADD KEY `submission_payment` (`paymentmethod`),
  ADD KEY `submission_country` (`deliveryCountry`),
  ADD KEY `submission_location` (`deliveryLocation`);

--
-- Indexes for table `transaction`
--
ALTER TABLE `transaction`
  ADD PRIMARY KEY (`transactionID`),
  ADD KEY `transaction_user` (`userName`),
  ADD KEY `transaction_payment` (`paymentmethod`),
  ADD KEY `transaction_country` (`deliveryCountry`),
  ADD KEY `transaction_jet` (`jetID`),
  ADD KEY `transaction_location` (`deliveryLocation`);

--
-- Indexes for table `userjet`
--
ALTER TABLE `userjet`
  ADD PRIMARY KEY (`userName`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cart`
--
ALTER TABLE `cart`
  MODIFY `cartID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=83;

--
-- AUTO_INCREMENT for table `submission`
--
ALTER TABLE `submission`
  MODIFY `submissionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=44;

--
-- AUTO_INCREMENT for table `transaction`
--
ALTER TABLE `transaction`
  MODIFY `transactionID` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=18;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `admin`
--
ALTER TABLE `admin`
  ADD CONSTRAINT `admin_user` FOREIGN KEY (`userName`) REFERENCES `userjet` (`userName`);

--
-- Constraints for table `airframe`
--
ALTER TABLE `airframe`
  ADD CONSTRAINT `Airframe_PrivateJet` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `cart`
--
ALTER TABLE `cart`
  ADD CONSTRAINT `jet_cart` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `user_cart` FOREIGN KEY (`userName`) REFERENCES `userjet` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `client`
--
ALTER TABLE `client`
  ADD CONSTRAINT `user_client` FOREIGN KEY (`userName`) REFERENCES `userjet` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `engine`
--
ALTER TABLE `engine`
  ADD CONSTRAINT `Engine_PrivateJet` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `exterior`
--
ALTER TABLE `exterior`
  ADD CONSTRAINT `Exterior_PrivateJet` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `interior`
--
ALTER TABLE `interior`
  ADD CONSTRAINT `Interior_PrivateJet` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `logindetailsjet`
--
ALTER TABLE `logindetailsjet`
  ADD CONSTRAINT `login_user` FOREIGN KEY (`userName`) REFERENCES `userjet` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `model`
--
ALTER TABLE `model`
  ADD CONSTRAINT `manufacturer_model` FOREIGN KEY (`manufacturerID`) REFERENCES `manufacturer` (`manufacturerID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `privatejet`
--
ALTER TABLE `privatejet`
  ADD CONSTRAINT `privatejet_model` FOREIGN KEY (`modeLID`) REFERENCES `model` (`modeLID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `submission`
--
ALTER TABLE `submission`
  ADD CONSTRAINT `submission_cart` FOREIGN KEY (`cartID`) REFERENCES `cart` (`cartID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `submission_country` FOREIGN KEY (`deliveryCountry`) REFERENCES `countrydelivery` (`isoCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `submission_location` FOREIGN KEY (`deliveryLocation`) REFERENCES `airports` (`airportCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `submission_payment` FOREIGN KEY (`paymentmethod`) REFERENCES `paymentmethod` (`methodID`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Constraints for table `transaction`
--
ALTER TABLE `transaction`
  ADD CONSTRAINT `transaction_country` FOREIGN KEY (`deliveryCountry`) REFERENCES `countrydelivery` (`isoCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_jet` FOREIGN KEY (`jetID`) REFERENCES `privatejet` (`jetID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_location` FOREIGN KEY (`deliveryLocation`) REFERENCES `airports` (`airportCode`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_payment` FOREIGN KEY (`paymentmethod`) REFERENCES `paymentmethod` (`methodID`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `transaction_user` FOREIGN KEY (`userName`) REFERENCES `userjet` (`userName`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
