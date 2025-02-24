Here is the SQL query to generate the required report:

```sql
SELECT 
    c.name,
    SUM(e.views) AS total_views,
    SUM(e.clicks) AS total_clicks,
    ROUND((SUM(e.clicks) / NULLIF(SUM(e.views), 0)) * 100, 2) AS engagement_rate
FROM campaigns c
JOIN engagements e ON c.id = e.campaign_id
WHERE c.is_active = 1
GROUP BY c.id, c.name
ORDER BY engagement_rate DESC, c.name ASC;
```

### Explanation:
1. **`JOIN engagements e ON c.id = e.campaign_id`** → Joins the `campaigns` and `engagements` tables on `campaign_id`.
2. **`WHERE c.is_active = 1`** → Filters only active campaigns.
3. **`SUM(e.views) AS total_views, SUM(e.clicks) AS total_clicks`** → Aggregates the total views and clicks per campaign.
4. **`ROUND((SUM(e.clicks) / NULLIF(SUM(e.views), 0)) * 100, 2) AS engagement_rate`** → Calculates engagement rate:
   - Uses `NULLIF(SUM(e.views), 0)` to prevent division by zero.
   - Multiplies by 100 to get the percentage.
   - Rounds to 2 decimal places.
5. **`ORDER BY engagement_rate DESC, c.name ASC`** → Sorts by engagement rate in descending order, then by name in ascending order.

Let me know if you need any modifications! 🚀