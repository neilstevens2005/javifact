package org.javifact.segment;

import org.javifact.segment.Segment;
import org.javifact.segment.AbstractSegment;
import org.javifact.segment.RawSegment;
/**
 * This represents an UNB (Interchange Header) segment.  The purpose of this segment is: To start, identify and specify an interchange.
 */
public class UNB extends AbstractSegment implements Segment {

	private SyntaxIdentifier syntaxIdentifier;
	private InterchangeSender interchangeSender;
	private InterchangeRecipient interchangeRecipient;
	private DateTimeOfPreparation dateTimeOfPreparation;
	private String interchangeControlAndReference;
	private RecipientsReferencePassword recipientsReferencePassword;
	private String applicationReference;
	private String processingPriorityCode;
	private String acknowledgementRequest;
	private String communicationsAggreementId;
	private String testIndicator;

	/**
	 * Construct a new UNB segment
	 */
	public UNB() {
	}

	/**
	 * Construct a new UNB segment from the given raw segment
	 * @param rawSegment The raw segment
	 */
	public UNB(RawSegment rawSegment) {
		SyntaxIdentifier syntaxIdentifier = new SyntaxIdentifier();
		syntaxIdentifier.setSyntaxIdentifier(rawSegment
				.getComponentDataElement(0, 0));
		syntaxIdentifier.setSyntaxVersionNumber(rawSegment
				.getComponentDataElement(0, 1));
		this.syntaxIdentifier = syntaxIdentifier;
		InterchangeSender interchangeSender = new InterchangeSender();
		interchangeSender.setSenderIdentification(rawSegment
				.getComponentDataElement(1, 0));
		interchangeSender.setPartnerIdentificationCodeQualifier(rawSegment
				.getComponentDataElement(1, 1));
		interchangeSender.setAddressForReverseRouting(rawSegment
				.getComponentDataElement(1, 2));
		this.interchangeSender = interchangeSender;
		InterchangeRecipient interchangeRecipient = new InterchangeRecipient();
		interchangeRecipient.setRecipientIdentification(rawSegment
				.getComponentDataElement(2, 0));
		interchangeRecipient.setPartnerIdentificationCodeQualifier(rawSegment
				.getComponentDataElement(2, 1));
		interchangeRecipient.setRoutingAddress(rawSegment
				.getComponentDataElement(2, 2));
		this.interchangeRecipient = interchangeRecipient;
		DateTimeOfPreparation dateTimeOfPreparation = new DateTimeOfPreparation();
		dateTimeOfPreparation.setDate(rawSegment.getComponentDataElement(3, 0));
		dateTimeOfPreparation.setTime(rawSegment.getComponentDataElement(3, 1));
		this.dateTimeOfPreparation = dateTimeOfPreparation;
		interchangeControlAndReference = rawSegment.getComponentDataElement(4,
				0);
		RecipientsReferencePassword recipientsReferencePassword = new RecipientsReferencePassword();
		recipientsReferencePassword.setRecipientsReferencePassword(rawSegment
				.getComponentDataElement(5, 0));
		recipientsReferencePassword
				.setRecipientsReferencePasswordQualifier(rawSegment
						.getComponentDataElement(5, 1));
		this.recipientsReferencePassword = recipientsReferencePassword;
		applicationReference = rawSegment.getComponentDataElement(6, 0);
		processingPriorityCode = rawSegment.getComponentDataElement(7, 0);
		acknowledgementRequest = rawSegment.getComponentDataElement(8, 0);
		communicationsAggreementId = rawSegment.getComponentDataElement(9, 0);
		testIndicator = rawSegment.getComponentDataElement(10, 0);
	}

	@Override
	public String getSegmentType() {
		return "UNB";
	}

	/**
	 * This represents a "Syntax Identifier" data element
	 */
	public static class SyntaxIdentifier {
		private String syntaxIdentifier;
		private String syntaxVersionNumber;

		/**
		 * Retrieves the "Syntax Identifier"
		 * @return The "Syntax Identifier" if it has been defined, otherwise null
		 */
		public String getSyntaxIdentifier() {
			return syntaxIdentifier;
		}

		/**
		 * Sets the "Syntax Identifier"
		 * @param syntaxIdentifier The new "Syntax Identifier"
		 */
		public void setSyntaxIdentifier(String syntaxIdentifier) {
			this.syntaxIdentifier = syntaxIdentifier;
		}

		/**
		 * Retrieves the "Syntax Version Number"
		 * @return The "Syntax Version Number" if it has been defined, otherwise null
		 */
		public String getSyntaxVersionNumber() {
			return syntaxVersionNumber;
		}

		/**
		 * Sets the "Syntax Version Number"
		 * @param syntaxVersionNumber The new "Syntax Version Number"
		 */
		public void setSyntaxVersionNumber(String syntaxVersionNumber) {
			this.syntaxVersionNumber = syntaxVersionNumber;
		}
	}

	/**
	 * Retrieves the "Syntax Identifier"
	 * @return The "Syntax Identifier" if it has been defined, otherwise null
	 */
	public SyntaxIdentifier getSyntaxIdentifier() {
		return syntaxIdentifier;
	}

	/**
	 * Sets the "Syntax Identifier"
	 * @param syntaxIdentifier The new "Syntax Identifier"
	 */
	public void setSyntaxIdentifier(SyntaxIdentifier syntaxIdentifier) {
		this.syntaxIdentifier = syntaxIdentifier;
	}

	/**
	 * This represents an "Interchange Sender" data element
	 */
	public static class InterchangeSender {
		private String senderIdentification;
		private String partnerIdentificationCodeQualifier;
		private String addressForReverseRouting;

		/**
		 * Retrieves the "Sender Identification"
		 * @return The "Sender Identification" if it has been defined, otherwise null
		 */
		public String getSenderIdentification() {
			return senderIdentification;
		}

		/**
		 * Sets the "Sender Identification"
		 * @param senderIdentification The new "Sender Identification"
		 */
		public void setSenderIdentification(String senderIdentification) {
			this.senderIdentification = senderIdentification;
		}

		/**
		 * Retrieves the "Partner Identification Code Qualifier"
		 * @return The "Partner Identification Code Qualifier" if it has been defined, otherwise null
		 */
		public String getPartnerIdentificationCodeQualifier() {
			return partnerIdentificationCodeQualifier;
		}

		/**
		 * Sets the "Partner Identification Code Qualifier"
		 * @param partnerIdentificationCodeQualifier The new "Partner Identification Code Qualifier"
		 */
		public void setPartnerIdentificationCodeQualifier(
				String partnerIdentificationCodeQualifier) {
			this.partnerIdentificationCodeQualifier = partnerIdentificationCodeQualifier;
		}

		/**
		 * Retrieves the "Address For Reverse Routing"
		 * @return The "Address For Reverse Routing" if it has been defined, otherwise null
		 */
		public String getAddressForReverseRouting() {
			return addressForReverseRouting;
		}

		/**
		 * Sets the "Address For Reverse Routing"
		 * @param addressForReverseRouting The new "Address For Reverse Routing"
		 */
		public void setAddressForReverseRouting(String addressForReverseRouting) {
			this.addressForReverseRouting = addressForReverseRouting;
		}
	}

	/**
	 * Retrieves the "Interchange Sender"
	 * @return The "Interchange Sender" if it has been defined, otherwise null
	 */
	public InterchangeSender getInterchangeSender() {
		return interchangeSender;
	}

	/**
	 * Sets the "Interchange Sender"
	 * @param interchangeSender The new "Interchange Sender"
	 */
	public void setInterchangeSender(InterchangeSender interchangeSender) {
		this.interchangeSender = interchangeSender;
	}

	/**
	 * This represents an "Interchange Recipient" data element
	 */
	public static class InterchangeRecipient {
		private String recipientIdentification;
		private String partnerIdentificationCodeQualifier;
		private String routingAddress;

		/**
		 * Retrieves the "Recipient Identification"
		 * @return The "Recipient Identification" if it has been defined, otherwise null
		 */
		public String getRecipientIdentification() {
			return recipientIdentification;
		}

		/**
		 * Sets the "Recipient Identification"
		 * @param recipientIdentification The new "Recipient Identification"
		 */
		public void setRecipientIdentification(String recipientIdentification) {
			this.recipientIdentification = recipientIdentification;
		}

		/**
		 * Retrieves the "Partner Identification Code Qualifier"
		 * @return The "Partner Identification Code Qualifier" if it has been defined, otherwise null
		 */
		public String getPartnerIdentificationCodeQualifier() {
			return partnerIdentificationCodeQualifier;
		}

		/**
		 * Sets the "Partner Identification Code Qualifier"
		 * @param partnerIdentificationCodeQualifier The new "Partner Identification Code Qualifier"
		 */
		public void setPartnerIdentificationCodeQualifier(
				String partnerIdentificationCodeQualifier) {
			this.partnerIdentificationCodeQualifier = partnerIdentificationCodeQualifier;
		}

		/**
		 * Retrieves the "Routing Address"
		 * @return The "Routing Address" if it has been defined, otherwise null
		 */
		public String getRoutingAddress() {
			return routingAddress;
		}

		/**
		 * Sets the "Routing Address"
		 * @param routingAddress The new "Routing Address"
		 */
		public void setRoutingAddress(String routingAddress) {
			this.routingAddress = routingAddress;
		}
	}

	/**
	 * Retrieves the "Interchange Recipient"
	 * @return The "Interchange Recipient" if it has been defined, otherwise null
	 */
	public InterchangeRecipient getInterchangeRecipient() {
		return interchangeRecipient;
	}

	/**
	 * Sets the "Interchange Recipient"
	 * @param interchangeRecipient The new "Interchange Recipient"
	 */
	public void setInterchangeRecipient(
			InterchangeRecipient interchangeRecipient) {
		this.interchangeRecipient = interchangeRecipient;
	}

	/**
	 * This represents a "Date / Time Of Preparation" data element
	 */
	public static class DateTimeOfPreparation {
		private String date;
		private String time;

		/**
		 * Retrieves the "Date"
		 * @return The "Date" if it has been defined, otherwise null
		 */
		public String getDate() {
			return date;
		}

		/**
		 * Sets the "Date"
		 * @param date The new "Date"
		 */
		public void setDate(String date) {
			this.date = date;
		}

		/**
		 * Retrieves the "Time"
		 * @return The "Time" if it has been defined, otherwise null
		 */
		public String getTime() {
			return time;
		}

		/**
		 * Sets the "Time"
		 * @param time The new "Time"
		 */
		public void setTime(String time) {
			this.time = time;
		}
	}

	/**
	 * Retrieves the "Date / Time Of Preparation"
	 * @return The "Date / Time Of Preparation" if it has been defined, otherwise null
	 */
	public DateTimeOfPreparation getDateTimeOfPreparation() {
		return dateTimeOfPreparation;
	}

	/**
	 * Sets the "Date / Time Of Preparation"
	 * @param dateTimeOfPreparation The new "Date / Time Of Preparation"
	 */
	public void setDateTimeOfPreparation(
			DateTimeOfPreparation dateTimeOfPreparation) {
		this.dateTimeOfPreparation = dateTimeOfPreparation;
	}

	/**
	 * Retrieves the "Interchange Control And Reference"
	 * @return The "Interchange Control And Reference" if it has been defined, otherwise null
	 */
	public String getInterchangeControlAndReference() {
		return interchangeControlAndReference;
	}

	/**
	 * Sets the "Interchange Control And Reference"
	 * @param interchangeControlAndReference The new "Interchange Control And Reference"
	 */
	public void setInterchangeControlAndReference(
			String interchangeControlAndReference) {
		this.interchangeControlAndReference = interchangeControlAndReference;
	}

	/**
	 * This represents a "Recipient’s Reference Password" data element
	 */
	public static class RecipientsReferencePassword {
		private String recipientsReferencePassword;
		private String recipientsReferencePasswordQualifier;

		/**
		 * Retrieves the "Recipient’s Reference/password"
		 * @return The "Recipient’s Reference/password" if it has been defined, otherwise null
		 */
		public String getRecipientsReferencePassword() {
			return recipientsReferencePassword;
		}

		/**
		 * Sets the "Recipient’s Reference/password"
		 * @param recipientsReferencePassword The new "Recipient’s Reference/password"
		 */
		public void setRecipientsReferencePassword(
				String recipientsReferencePassword) {
			this.recipientsReferencePassword = recipientsReferencePassword;
		}

		/**
		 * Retrieves the "Recipient’s Reference/password Qualifier"
		 * @return The "Recipient’s Reference/password Qualifier" if it has been defined, otherwise null
		 */
		public String getRecipientsReferencePasswordQualifier() {
			return recipientsReferencePasswordQualifier;
		}

		/**
		 * Sets the "Recipient’s Reference/password Qualifier"
		 * @param recipientsReferencePasswordQualifier The new "Recipient’s Reference/password Qualifier"
		 */
		public void setRecipientsReferencePasswordQualifier(
				String recipientsReferencePasswordQualifier) {
			this.recipientsReferencePasswordQualifier = recipientsReferencePasswordQualifier;
		}
	}

	/**
	 * Retrieves the "Recipient’s Reference Password"
	 * @return The "Recipient’s Reference Password" if it has been defined, otherwise null
	 */
	public RecipientsReferencePassword getRecipientsReferencePassword() {
		return recipientsReferencePassword;
	}

	/**
	 * Sets the "Recipient’s Reference Password"
	 * @param recipientsReferencePassword The new "Recipient’s Reference Password"
	 */
	public void setRecipientsReferencePassword(
			RecipientsReferencePassword recipientsReferencePassword) {
		this.recipientsReferencePassword = recipientsReferencePassword;
	}

	/**
	 * Retrieves the "Application Reference"
	 * @return The "Application Reference" if it has been defined, otherwise null
	 */
	public String getApplicationReference() {
		return applicationReference;
	}

	/**
	 * Sets the "Application Reference"
	 * @param applicationReference The new "Application Reference"
	 */
	public void setApplicationReference(String applicationReference) {
		this.applicationReference = applicationReference;
	}

	/**
	 * Retrieves the "Processing Priority Code"
	 * @return The "Processing Priority Code" if it has been defined, otherwise null
	 */
	public String getProcessingPriorityCode() {
		return processingPriorityCode;
	}

	/**
	 * Sets the "Processing Priority Code"
	 * @param processingPriorityCode The new "Processing Priority Code"
	 */
	public void setProcessingPriorityCode(String processingPriorityCode) {
		this.processingPriorityCode = processingPriorityCode;
	}

	/**
	 * Retrieves the "Acknowledgement Request"
	 * @return The "Acknowledgement Request" if it has been defined, otherwise null
	 */
	public String getAcknowledgementRequest() {
		return acknowledgementRequest;
	}

	/**
	 * Sets the "Acknowledgement Request"
	 * @param acknowledgementRequest The new "Acknowledgement Request"
	 */
	public void setAcknowledgementRequest(String acknowledgementRequest) {
		this.acknowledgementRequest = acknowledgementRequest;
	}

	/**
	 * Retrieves the "Communications Aggreement Id"
	 * @return The "Communications Aggreement Id" if it has been defined, otherwise null
	 */
	public String getCommunicationsAggreementId() {
		return communicationsAggreementId;
	}

	/**
	 * Sets the "Communications Aggreement Id"
	 * @param communicationsAggreementId The new "Communications Aggreement Id"
	 */
	public void setCommunicationsAggreementId(String communicationsAggreementId) {
		this.communicationsAggreementId = communicationsAggreementId;
	}

	/**
	 * Retrieves the "Test Indicator"
	 * @return The "Test Indicator" if it has been defined, otherwise null
	 */
	public String getTestIndicator() {
		return testIndicator;
	}

	/**
	 * Sets the "Test Indicator"
	 * @param testIndicator The new "Test Indicator"
	 */
	public void setTestIndicator(String testIndicator) {
		this.testIndicator = testIndicator;
	}

	@Override
	protected RawSegment toRawSegment() {
		RawSegment rawSegment = new RawSegment();
		if (syntaxIdentifier != null) {
			rawSegment.setComponentDataElement(0, 0,
					syntaxIdentifier.getSyntaxIdentifier());
			rawSegment.setComponentDataElement(0, 1,
					syntaxIdentifier.getSyntaxVersionNumber());
		}
		if (interchangeSender != null) {
			rawSegment.setComponentDataElement(1, 0,
					interchangeSender.getSenderIdentification());
			rawSegment.setComponentDataElement(1, 1,
					interchangeSender.getPartnerIdentificationCodeQualifier());
			rawSegment.setComponentDataElement(1, 2,
					interchangeSender.getAddressForReverseRouting());
		}
		if (interchangeRecipient != null) {
			rawSegment.setComponentDataElement(2, 0,
					interchangeRecipient.getRecipientIdentification());
			rawSegment.setComponentDataElement(2, 1, interchangeRecipient
					.getPartnerIdentificationCodeQualifier());
			rawSegment.setComponentDataElement(2, 2,
					interchangeRecipient.getRoutingAddress());
		}
		if (dateTimeOfPreparation != null) {
			rawSegment.setComponentDataElement(3, 0,
					dateTimeOfPreparation.getDate());
			rawSegment.setComponentDataElement(3, 1,
					dateTimeOfPreparation.getTime());
		}
		rawSegment
				.setComponentDataElement(4, 0, interchangeControlAndReference);
		if (recipientsReferencePassword != null) {
			rawSegment.setComponentDataElement(5, 0,
					recipientsReferencePassword
							.getRecipientsReferencePassword());
			rawSegment.setComponentDataElement(5, 1,
					recipientsReferencePassword
							.getRecipientsReferencePasswordQualifier());
		}
		rawSegment.setComponentDataElement(6, 0, applicationReference);
		rawSegment.setComponentDataElement(7, 0, processingPriorityCode);
		rawSegment.setComponentDataElement(8, 0, acknowledgementRequest);
		rawSegment.setComponentDataElement(9, 0, communicationsAggreementId);
		rawSegment.setComponentDataElement(10, 0, testIndicator);
		return rawSegment;
	}
}