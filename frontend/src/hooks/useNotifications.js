import { useEffect, useState, useContext } from "react";
import { AuthContext } from "../auth/AuthContext";
import Notification from "../components/common/Notification";

/**
 * Hook to connect to notification service SSE endpoint
 * Returns notifications array and a function to remove notifications
 */
export const useNotifications = () => {
  const { user } = useContext(AuthContext);
  const [notifications, setNotifications] = useState([]);
  const [eventSource, setEventSource] = useState(null);

  useEffect(() => {
    if (!user?.email) return;

    // Connect to SSE endpoint
    const token = localStorage.getItem("token");
    const email = encodeURIComponent(user.email);
    const eventSource = new EventSource(
      `http://localhost:8080/api/notifications/subscribe/${email}`,
      {
        headers: token ? { Authorization: `Bearer ${token}` } : {},
      }
    );

    eventSource.onopen = () => {
      console.log("Connected to notification service");
    };

    eventSource.addEventListener("connected", (event) => {
      console.log("Notification service connected:", event.data);
    });

    eventSource.addEventListener("notification", (event) => {
      try {
        const data = JSON.parse(event.data);
        setNotifications((prev) => [
          ...prev,
          {
            id: Date.now(),
            message: data.message || "New notification",
            type: data.type === "RIDE" || data.type === "PAYMENT" ? "success" : "info",
          },
        ]);
      } catch (err) {
        console.error("Error parsing notification:", err);
      }
    });

    eventSource.onerror = (error) => {
      console.error("SSE connection error:", error);
      eventSource.close();
    };

    setEventSource(eventSource);

    return () => {
      if (eventSource) {
        eventSource.close();
      }
    };
  }, [user?.email]);

  const removeNotification = (id) => {
    setNotifications((prev) => prev.filter((n) => n.id !== id));
  };

  return { notifications, removeNotification };
};

